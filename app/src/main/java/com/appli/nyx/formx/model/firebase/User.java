package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.model.firebase.enumeration.Gender;

import java.io.Serializable;

public class User implements Serializable {

	public String email;

	public String name;

	public String firstName;

	public String telephone;

	public Gender gender;

	public String birthDay;

	public String birthPlace;

}
