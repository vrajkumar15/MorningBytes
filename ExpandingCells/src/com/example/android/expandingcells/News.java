package com.example.android.expandingcells;

public class News {
	public String Image;
	public String Body;
	public String Topic;

	public News(String image, String topic, String body) {
		this.Image = image;
		this.Topic = topic;
		this.Body = body;
	}

	public News() {
		// TODO Auto-generated constructor stub
	}

	public String getImage() {
		return this.Image;
	}

	public void setImage(String image) {
		this.Image = image;
	}

	public String getBody() {
		return this.Body;
	}

	public void setBody(String body) {
		this.Body = body;
	}
	
	public String getTopic() {
		return this.Topic;
	}

	public void setTopic(String topic) {
		this.Topic = topic;
	}

	public void setNews(String image, String title, String body) {
		this.Image = image;
		this.Topic = title;
		this.Body = body;
		
	}
	

}
