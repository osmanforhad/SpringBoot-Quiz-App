package com.devrezaur.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devrezaur.main.model.Question;
import com.devrezaur.main.model.QuestionForm;
import com.devrezaur.main.model.Result;
import com.devrezaur.main.repository.QuestionRepo;
import com.devrezaur.main.repository.ResultRepo;

@Service
public class QuizService {
	
	@Autowired
	Question question;//inject Question class as dependency
	@Autowired
	QuestionForm qForm;//inject QuestionForm class as dependency
	@Autowired
	QuestionRepo qRepo;//inject QuestionRepo class as dependency
	@Autowired
	Result result;//inject Result class as dependency
	@Autowired
	ResultRepo rRepo;//inject ResultRepo class as dependency
	
	//method for get data from DB
	public QuestionForm getQuestions() {
		List<Question> allQues = qRepo.findAll();
		List<Question> qList = new ArrayList<Question>();
		
		Random random = new Random();
		
		for(int i=0; i<5; i++) {
			int rand = random.nextInt(allQues.size());
			qList.add(allQues.get(rand));
			allQues.remove(rand);
		}
		qForm.setQuestions(qList);
		return qForm;
	}
	
	//method check correct answers and result
	public int getResult(QuestionForm qForm) {
		int correct = 0;
		for(Question q : qForm.getQuestions()) {
			if(q.getAns() == q.getChose())
				correct++;
		}
		return correct;
	}
	
	//method for save score in DB
	public void saveScore(Result result) {
		Result saveResult = new Result();
		saveResult.setUsername(result.getUsername());
		saveResult.setTotalCorrect(result.getTotalCorrect());
		rRepo.save(saveResult);
	}
	
	//method for fetch all result from DB
	public List<Result> getTopScore(){
		List<Result> sList = rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
		return sList;
	}
	
}
