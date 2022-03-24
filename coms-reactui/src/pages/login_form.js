import React , { useState ,useEffect } from 'react';
import ReactDOM from 'react-dom';
import Axios from 'axios'; 
const LoginForm = (props) => {

    const [errorMessages, setErrorMessages] = useState({});
    const [isSubmitted, setIsSubmitted] = useState(false);
    const handleSubmit = (event) => {
        //Prevent page reload
        event.preventDefault();
    
        var { uemail, password } = document.forms[0];
        const requestbody = {
            "username":uemail.value,    
            "password": password.value
            };
        Axios.post('/authenticate', requestbody)
        .then(res=>{
            props.handleLoginsuccess(res.data.token, uemail.value);
  
        }).catch(function (error) {
            if (error.response) {
                console.log("login fail");
            setErrorMessages({ name: "password", message: "invalid user email/password" });
              console.log(error.response.data);
              console.log(error.response.status);
              console.log(error.response.headers);
            }
        });
      };
      const renderErrorMessage = (name) =>
        name === errorMessages.name && (
        <div className="error">{errorMessages.message}</div>
        );
        /*const handleRegister = (event) =>
        {
            alert("hi");
        }*/
        const handleRegister = (page)=> {
            props.handleRegisterClick(page);
          } 
    return (
        <form className="form-signin" onSubmit={handleSubmit}>
            <h1 className="h3 mb-3 font-weight-normal">Please sign in</h1>
            <label htmlFor="inputEmail" className="sr-only">Email address</label>
            <input type="email" name="uemail" id="uemail" className="form-control" placeholder="Email address" required autoFocus/>
            <label htmlFor="inputPassword" className="sr-only">Password</label>
            <input type="password" name="password" id="password" className="form-control" placeholder="Password" required/>
            {renderErrorMessage("password")}
            <button className="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            Don't have an account? <a href="#" onClick={()=>handleRegister("RegisterUser")}>Register</a> 
            <p className="mt-5 mb-3 text-muted">&copy; 2022-2023</p>
    </form>
    );
};
export default LoginForm;