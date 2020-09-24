package com.project.teikametricsproj.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.project.teikametrics.models.Commit;
import com.project.teikametrics.models.User;

@RestController
public class GitHubService {
	
	public static final String BASE_URL = "https://api.github.com/repos/ashishkumar6707/TeikametricsProject/commits";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/")
	public List<String> message(){
		Commit[] commits = restTemplate.getForObject(
				BASE_URL, Commit[].class);
		List<Commit> commitList = new ArrayList<>();
		
		for(int i=0; i<10 && i<commits.length; i++){//api already display in order of commits time (more recent one first)
			commitList.add(commits[i]);
		}
		
		List<String> messageList = new ArrayList<>();
		Map<String, Integer> wordMap = new HashMap<>();
		StringBuilder allCommitMessages = new StringBuilder();
		
		messageList = commitList.stream().map(t->t.getCommit().getMessage()).collect(Collectors.toList());
		messageList.stream().forEach(m->allCommitMessages.append(m+" "));

		//ignoring case, converting to lower case
		String[] words = allCommitMessages.toString().toLowerCase().split("\\W+");
		for(String word : words){
			wordMap.put(word, wordMap.getOrDefault(word, 0)+1);
		}
		
		PriorityQueue<String> pq= new PriorityQueue<String>(
				(a,b) -> {
					int frequency1 = wordMap.get(a);
					int frequency2 = wordMap.get(b);
					if(frequency1==frequency2) return b.compareTo(a);
					return frequency1-frequency2;
				});
		wordMap.keySet().forEach(a->{
			pq.add(a);
			if(pq.size()>5) pq.poll();
		});
		
		List<String> frequentWord =  new ArrayList<>();
		while (!pq.isEmpty()) frequentWord.add(pq.poll());
		
		Collections.reverse(frequentWord);
		//STDOUT 5 most frequent word used in commit message
		System.out.println("5 most frequent word used in commit are : "+frequentWord);
		
		List<Integer> hourList = commitList.stream().map(t->{
			User author = t.getCommit().getAuthor();
			if(author!=null && author.getDate()!=null){
				return Integer.parseInt(author.getDate().substring(11, 13));
			}	
			return null;
		}).collect(Collectors.toList());
		Map<Integer, Integer> hourFrequencyMap = new HashMap<>();
		for(Integer hour : hourList){
			if(hour !=null)
				hourFrequencyMap.put(hour, hourFrequencyMap.getOrDefault(hour, 0)+1);
		}
		int max = 0;
		Integer reqHour = null;
		for(Entry<Integer,Integer> entry: hourFrequencyMap.entrySet()){
			if(entry.getValue()>max){
				max=entry.getValue();
				reqHour=entry.getKey();
			}
		}
		//STDOUT most commit at Hour
		System.out.println("Most Commit at Hour : "+reqHour);
		return messageList;
	}

}
