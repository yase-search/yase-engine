package fr.imie.yase.dto;

/**
 * DTO Keywords
 * @author Erwan
 *
 */
public class Keywords {

	private boolean pertinent;
	
	private Integer id;

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
	
}
