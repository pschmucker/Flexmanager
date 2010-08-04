package flexcom.casehistory.mvc.controller;

import javax.validation.constraints.NotNull;

public class ChangePasswordCommand {
	
	private long userId;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	public ChangePasswordCommand() {
		this(0);
	}

	public ChangePasswordCommand(long id) {
		userId = id;
		oldPassword = "";
		newPassword = "";
		confirmPassword = "";
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@NotNull
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@NotNull
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@NotNull
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
