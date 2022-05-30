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

	uemail.value = "admin@coms.com";
	password.value = "password";
    
        const requestbody = {
            "username":uemail.value,    
            "password": password.value
            };
        
        const token = 'ergr82743mwenwne8237823masas';
        const user = {"id":11261, "userId": "admin@coms.com","firstName": "Sibendu", "lastName": "Das", "roles": ["SUPERADMIN"] };    
        props.handleLoginsuccess(token, user);

        /*    
        Axios.post('/authenticate', requestbody)
        .then(res=>{
            props.handleLoginsuccess(res.data.token, res.data.user);
  
        }).catch(function (error) {
            if (error.response) {
              console.log("login fail");
              setErrorMessages({ name: "password", message: "Invalid email/password" });
              console.log(error.response.data);
              console.log(error.response.status);
              console.log(error.response.headers);
            }
        });
        */
	
	/* Sample call with Bearer token in header
	let headers = { "Authorization": 'Bearer ' + token};
        Axios.get('/getuser',{
                  headers: headers
            })
            .then(response => 
            {
                console.log(response.data);
                setLoggeduser(response.data);
                setPage("Home");
                setLoginstatus(true);
            });     
	*/
		
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
        <div id="layoutDrawer_content">
        
        <main>
        <div className="container-xl p-5">
            <div className="row justify-content-center">
            <div className="col-xxl-4 col-xl-5 col-lg-6 col-md-8">
        <form className="form-signin" onSubmit={handleSubmit}>
            <h1 className="h3 mb-3 font-weight-normal">Please sign in</h1>
            <label htmlFor="inputEmail" className="sr-only">Email address</label>
            <input type="email" name="uemail" id="uemail" className="form-control" placeholder="Email address" autoFocus/>
            <label htmlFor="inputPassword" className="sr-only">Password</label>
            <input type="password" name="password" id="password" className="form-control" placeholder="Password"/>
            {renderErrorMessage("password")}
            <button className="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            Don't have an account? <a href="#" onClick={()=>handleRegister("RegisterUser")}>Register</a> 
            <p className="mt-5 mb-3 text-muted">&copy; 2022-2023</p>
        </form>
        </div>
        </div>
        </div>
     </main>
    </div>
    );
};
export default LoginForm;