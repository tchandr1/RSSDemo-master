package com.iitevents.util;

public class Sports {
	private int id;
    private String name;
    private String url;
    
    public Sports(){}
 
    public Sports(String name, String url) {
        super();
        this.name = name;
        this.url = url;
    }
    
    //getters & setters
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Sports [id=" + id + ", name=" + name + ", url=" + url + "]";
	}
	

}
