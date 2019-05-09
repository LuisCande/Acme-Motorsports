
package domain;

public enum Priority {

	HIGH("HIGH", 1), MEDIUM("MEDIUM", 2), LOW("LOW", 3);

	private int		id;
	private String	name;


	private Priority(final String name, final int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getId() {
		return this.id;

	}
}
