package com.example.subramanyam.popularmoviespart2.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TrailerResponse{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<TrailerData> results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<TrailerData> results){
		this.results = results;
	}

	public List<TrailerData> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"Trailer{" + 
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}