package fr.imie.yase.dto;

/**
 * DTO Keywords
 * @author Erwan
 *
 */
public class Keywords {

	private boolean pertinent;
	
	private Integer id;
	
	private String value;

	public Keywords(){

	}

	public Keywords(String value, boolean pertinent, Integer id) {
		this.value = value;
		this.pertinent = pertinent;
		this.id = id;
	}

	/**
	 * @return the pertinent
	 */
	public boolean isPertinent() {
		return pertinent;
	}

	/**
	 * @param pertinent the pertinent to set
	 */
	public void setPertinent(boolean pertinent) {
		this.pertinent = pertinent;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
