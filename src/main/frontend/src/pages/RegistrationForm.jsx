import axios from "axios";
import React, {useState} from "react";

export const RegistrationForm = () => {

  const [user, setUser] = useState({
    firstName : '',
    lastName : '',
    password : '',
    email : '',
    phone : ''
  });

  const handleSubmit = (event) => {
      event.preventDefault();
      axios.post('http://localhost:8080/registration', {
        firstName : user.firstName,
        lastName : user.lastName,
        password : user.password,
        email : user.email,
        phone : user.phone,
      }).then(res => {
        axios.get('http://localhost:8080/registration/confirm?token='.concat(res.data)).then(res => {console.log(res)});
      });
  };

  const handleFirstName = (event) => {
    event.preventDefault();
    setUser(existingValues => ({
      ...existingValues,
      firstName: event.target.value}));
    console.log(user.firstName);
  }

  const handleLastName = (event) => {
    event.preventDefault();
    setUser(existingValues => ({
      ...existingValues,
      lastName: event.target.value}));
    console.log(user.lastName);
  }

  const handlePassword = (event) => {
    event.preventDefault();
    setUser(existingValues => ({
      ...existingValues,
      password: event.target.value}));
    console.log(user.password);
  }

  const handleEmail = (event) => {
    event.preventDefault();
    setUser(existingValues => ({
      ...existingValues,
      email: event.target.value}));
    console.log(user.email);
  }

  const handlePhone = (event) => {
    event.preventDefault();
    setUser(existingValues => ({
      ...existingValues,
      phone: event.target.value}));
    console.log(user.phone);
  }
//TODO send confirmation token to the server
  const renderForm = (
    <div className="form">
      <form onSubmit={handleSubmit}>

        <div className="input-container">
          <label>First Name </label>
          <input type="text" name="firstNameInput" value={user.firstName} onChange={handleFirstName} required />  
        </div>

        <div className="input-container">
          <label>Last Name </label>
          <input type="text" name="lastNameInput" value={user.lastName} onChange={handleLastName} required />
        </div>

        <div className="input-container">
          <label>Password </label>
          <input type="password" name="passwordInput" value={user.password} onChange={handlePassword} required />
        </div>

        <div className="input-container">
          <label>Email </label>
          <input type="text" name="emailInput" value={user.email} onChange={handleEmail} required />
        </div>

        <div className="input-container">
          <label>Phone </label>
          <input type="text" name="phoneInput" value={user.phone} onChange={handlePhone} required />
        </div>

        <div className="button-container">
          <input type="submit" />
        </div>

      </form>
    </div>
  );

    return(
        <div className="login-form">
            <div className="title">Sign In</div>
            {renderForm}
        </div>
    )
};