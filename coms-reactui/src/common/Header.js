import React , { useState } from 'react';

function Header(props) {

    const handleClick = (page)=> {
        console.log('clicked header:'+ page);
        props.onClick(page);
    }; 

    const alerts = [
        {"id":23312, "date": "Mar 12, 2021", "text": "Sample message 1"},
        {"id":23313, "date": "Mar 11, 2021", "text": "Sample message 2"}
    ]; 

    function hideLeftMenu(){        
        var menu = document.getElementById("layoutDrawer_nav");
        alert('Clicked '+menu.style.display);
        if (menu.style.display === "none") {
            menu.style.display = "block";
        } else {
            menu.style.display = "none";
        }
    }

    return (

        <div id="layoutDrawer_top">

        <nav className="top-app-bar navbar navbar-expand navbar-dark bg-dark">
            <div className="container-fluid px-4">

                <button className="btn btn-lg btn-icon order-1 order-lg-0" id="drawerToggle" href="#"><i className="material-icons">menu</i></button>

                <a className="navbar-brand me-auto" href="#"><div className="text-uppercase font-monospace">COMS Portal</div></a>

                <div className="d-flex align-items-center mx-3 me-lg-0">

                    <ul className="navbar-nav d-none d-lg-flex">
                        <li className="nav-item"><a className="nav-link" onClick={()=>handleClick("NewTask")}>Overview</a></li>
                    </ul>

                    <div className="d-flex">
                        <div className="dropdown dropdown-notifications d-none d-sm-block">

                            <button className="btn btn-lg btn-icon dropdown-toggle me-3" id="dropdownMenuNotifications" type="button" data-bs-toggle="dropdown" aria-expanded="false"><i className="material-icons">notifications</i></button>

                            <ul className="dropdown-menu dropdown-menu-end me-3 mt-3 py-0 overflow-hidden" aria-labelledby="dropdownMenuNotifications">
                                <li key="keyAlerts"><h6 className="dropdown-header bg-primary text-white fw-500 py-3">Alerts</h6></li>
                                {   
                                    React.Children.toArray(
                                        alerts.map(({ id, date, text }) => 
                                            <>  
                                            <li>
                                                <a className="dropdown-item unread" href="#!">
                                                    <div className="dropdown-item-content">
                                                        <div className="dropdown-item-content-subtext">{date} · {text}</div>
                                                    </div>
                                                </a>
                                            </li>
                                            <li><hr className="dropdown-divider my-0" /></li>
                                            </>
                                        )
                                    )    
                                }
                            </ul>
                        </div>
                        <div className="dropdown">
                            <button className="btn btn-lg btn-icon dropdown-toggle" id="dropdownMenuProfile" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                {
                                     props.user?
                                     <i className="material-icons">person</i> :
                                     <i className="material-icons" onClick={()=>handleClick("Login")}>login</i>
                                }
                            </button>

                            {
                                 props.user?
                                    <ul className="dropdown-menu dropdown-menu-end mt-3" aria-labelledby="dropdownMenuProfile">
                                        <li key="keyProfile">
                                            <a className="dropdown-item" href="!#" onClick={()=>handleClick("Profile")}>
                                                <i className="material-icons leading-icon">person</i>
                                                <div className="me-3">Profile</div>
                                            </a>
                                        </li>
                                        <li key="keyPref">
                                            <a className="dropdown-item" href="!#" onClick={()=>handleClick("Preferences")}>
                                                <i className="material-icons leading-icon">settings</i>
                                                <div className="me-3">Preferences</div>
                                            </a>
                                        </li>
                                        <li key="keyHelp">
                                            <a className="dropdown-item" href="https://www.google.com" target="_blank" rel="noreferrer">
                                                <i className="material-icons leading-icon">help</i>
                                                <div className="me-3">Help</div>
                                            </a>
                                        </li>
                                        <li><hr className="dropdown-divider" /></li>
                                        <li key="keyLogout">
                                            <a className="dropdown-item" href="#!"  onClick={() => props.onLogout()}>
                                                <i className="material-icons leading-icon">logout</i>
                                                <div className="me-3">Logout</div>
                                            </a>
                                        </li>
                                    </ul>
                            :
                            <ul className="dropdown-menu dropdown-menu-end mt-3" aria-labelledby="dropdownMenuProfile">
                                </ul>
                            }
                        </div>
                    </div>
                </div>
            </div>
        </nav>
                            
        </div>                    
    );
}
export default Header;