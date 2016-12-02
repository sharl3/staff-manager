package perso.sharl3.entity;

public class Developer {
	private String id;
	private String name;
	private String surname;
	private String projectLabel;
	

	public Developer(String id, String name, String surname, String projectLabel) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.projectLabel = projectLabel;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getProjectLabel() {
		return projectLabel;
	}
	public void setProjectLabel(String projectLabel) {
		this.projectLabel = projectLabel;
	}
	
	public String toString(String separator) {
		return name + separator + surname + separator + id + separator + projectLabel;
	}
}
