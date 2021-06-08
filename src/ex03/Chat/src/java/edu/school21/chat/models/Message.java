package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {

	private LocalDateTime messageDateTime;
	private Long id;
	private User author;
	private Chatroom chatroom;
	private String text;
	public Message(Long id, User author, Chatroom chatroom, String text, LocalDateTime messageDateTime) {
		this.id = id;
		this.author = author;
		this.chatroom = chatroom;
		this.text = text;
		this.messageDateTime = messageDateTime;
	}

	public LocalDateTime getMessageDateTime() {
		return messageDateTime;
	}

	public void setMessageDateTime(LocalDateTime messageDateTime) {
		this.messageDateTime = messageDateTime;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Chatroom getChatroom() {
		return chatroom;
	}

	public void setChatroom(Chatroom chatroom) {
		this.chatroom = chatroom;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return id.equals(message.id) && author.equals(message.author) && chatroom.equals(message.chatroom) && text.equals(message.text) && messageDateTime.equals(message.messageDateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, author, chatroom, text, messageDateTime);
	}

	@Override
	public String toString() {
		return "Message{\n" +
				"\tid=" + id + ",\n" +
				"\tauthor=" + author.toString() + ",\n" +
				"\tchatroom=" + chatroom.toString() + ",\n" +
				"\ttext=\"" + text + '\"' + ",\n" +
				"\tmessageDateTime=" + messageDateTime.format(DateTimeFormatter.ofPattern("MM/MM/yy HH:mm")) +
				"\n}";
	}
}
