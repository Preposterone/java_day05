package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
	private final List<Message> roomMessages;
	private Long id;
	private String name;
	private User owner;

	public Chatroom(long id, String name, User owner, List<Message> roomMessages) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.roomMessages = roomMessages;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Chatroom chatroom = (Chatroom) o;
		return id.equals(chatroom.id) && name.equals(chatroom.name) && owner.equals(chatroom.owner) && roomMessages.equals(chatroom.roomMessages);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, owner, roomMessages);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Chatroom{" +
				"id=" + id +
				", name='" + name + '\'' +
				", owner=" + owner.getLogin() +
				", roomMessages=" + roomMessages +
				'}';
	}

}
