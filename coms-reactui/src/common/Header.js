import React , { useState ,useEffect } from 'react';
import ReactDOM from 'react-dom';
function Header(props) {
    const handleClick = (page)=> {
        console.log('page1 clicked:'+ page);
        props.onClick(page);
      } 
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">

            <button type="button" id="sidebarCollapse" className="navbar-btn">
                <span></span>
                <span></span>
                <span></span>
            </button>
            <button className="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <i className="fas fa-align-justify"></i>
            </button>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="nav navbar-nav ml-auto">
                    <li className="nav-item active">
                        {
                            props.Loginstatus? <span>Welcome {props.Useremail}</span>  :
                            <a className="nav-link" href="#" onClick={()=>handleClick("Login")}>login</a>
                        }
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    );
}
export default Header;