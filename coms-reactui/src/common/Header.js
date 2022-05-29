import React from 'react';
function Header(props) {
    const handleClick = (page)=> {
        console.log('page1 clicked:'+ page);
        props.onClick(page);
      } 
    return (

        <nav className="top-app-bar navbar navbar-expand navbar-dark bg-dark">
            <div className="container-fluid px-4">

                <button className="btn btn-lg btn-icon order-1 order-lg-0" id="drawerToggle" href="#"><i className="material-icons">menu</i></button>

                <a className="navbar-brand me-auto" href="index.html"><div className="text-uppercase font-monospace">COMS Portal</div></a>

                <div className="d-flex align-items-center mx-3 me-lg-0">

                    <ul className="navbar-nav d-none d-lg-flex">
                        <li className="nav-item"><a className="nav-link" href="index.html">Overview</a></li>
                        <li className="nav-item"><a className="nav-link" href="https://docs.startbootstrap.com/material-admin-pro" target="_blank" rel="noreferrer">Documentation</a></li>
                    </ul>

                    <div className="d-flex">

                        <div className="dropdown dropdown-notifications d-none d-sm-block">
                            <button className="btn btn-lg btn-icon dropdown-toggle me-3" id="dropdownMenuMessages" type="button" data-bs-toggle="dropdown" aria-expanded="false"><i className="material-icons">mail_outline</i></button>
                            <ul className="dropdown-menu dropdown-menu-end me-3 mt-3 py-0 overflow-hidden" aria-labelledby="dropdownMenuMessages">
                                <li><h6 className="dropdown-header bg-primary text-white fw-500 py-3">Messages</h6></li>
                                <li><hr className="dropdown-divider my-0" /></li>
                                <li>
                                    <a className="dropdown-item unread" href="#!">
                                        <div className="dropdown-item-content">
                                            <div className="dropdown-item-content-text"><div className="text-truncate d-inline-block">Hi there, I had a question about something, is there any way you can help me out?</div></div>
                                            <div className="dropdown-item-content-subtext">Mar 12, 2021 · Juan Babin</div>
                                        </div>
                                    </a>
                                </li>
                                <li><hr className="dropdown-divider my-0" /></li>
                                <li>
                                    <a className="dropdown-item" href="#!">
                                        <div className="dropdown-item-content">
                                            <div className="dropdown-item-content-text"><div className="text-truncate d-inline-block" >Thanks for the assistance the other day, I wanted to follow up with you just to make sure everyting is settled.</div></div>
                                            <div className="dropdown-item-content-subtext">Mar 10, 2021 · Christine Hendersen</div>
                                        </div>
                                    </a>
                                </li>
                                <li><hr className="dropdown-divider my-0" /></li>
                                <li>
                                    <a className="dropdown-item" href="#!">
                                        <div className="dropdown-item-content">
                                            <div className="dropdown-item-content-text"><div className="text-truncate d-inline-block" >Welcome to our group! It's good to see new members and I know you will do great!</div></div>
                                            <div className="dropdown-item-content-subtext">Mar 8, 2021 · Celia J. Knight</div>
                                        </div>
                                    </a>
                                </li>
                                <li><hr className="dropdown-divider my-0" /></li>
                                <li>
                                    <a className="dropdown-item py-3" href="#!">
                                        <div className="d-flex align-items-center w-100 justify-content-end text-primary">
                                            <div className="fst-button small">View all</div>
                                            <i className="material-icons icon-sm ms-1">chevron_right</i>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div className="dropdown dropdown-notifications d-none d-sm-block">
                            <button className="btn btn-lg btn-icon dropdown-toggle me-3" id="dropdownMenuNotifications" type="button" data-bs-toggle="dropdown" aria-expanded="false"><i className="material-icons">notifications</i></button>
                            <ul className="dropdown-menu dropdown-menu-end me-3 mt-3 py-0 overflow-hidden" aria-labelledby="dropdownMenuNotifications">
                                <li><h6 className="dropdown-header bg-primary text-white fw-500 py-3">Alerts</h6></li>
                                <li><hr className="dropdown-divider my-0" /></li>
                                <li>
                                    <a className="dropdown-item unread" href="#!">
                                        <i className="material-icons leading-icon">assessment</i>
                                        <div className="dropdown-item-content me-2">
                                            <div className="dropdown-item-content-text">Your March performance report is ready to view.</div>
                                            <div className="dropdown-item-content-subtext">Mar 12, 2021 · Performance</div>
                                        </div>
                                    </a>
                                </li>
                                <li><hr className="dropdown-divider my-0" /></li>
                                <li>
                                    <a className="dropdown-item" href="#!">
                                        <i className="material-icons leading-icon">check_circle</i>
                                        <div className="dropdown-item-content me-2">
                                            <div className="dropdown-item-content-text">Tracking codes successfully updated.</div>
                                            <div className="dropdown-item-content-subtext">Mar 12, 2021 · Coverage</div>
                                        </div>
                                    </a>
                                </li>
                                <li><hr className="dropdown-divider my-0" /></li>
                               
                                <li><hr className="dropdown-divider my-0" /></li>
                                <li>
                                    <a className="dropdown-item py-3" href="#!">
                                        <div className="d-flex align-items-center w-100 justify-content-end text-primary">
                                            <div className="fst-button small">View all</div>
                                            <i className="material-icons icon-sm ms-1">chevron_right</i>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div className="dropdown">
                            <button className="btn btn-lg btn-icon dropdown-toggle" id="dropdownMenuProfile" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                {
                                     props.Loginstatus?
                                     <i className="material-icons">person</i> :
                                     <i className="material-icons" onClick={()=>handleClick("Login")}>login</i>
                                }
                            </button>
                            {
                                 props.Loginstatus?
                                    <ul className="dropdown-menu dropdown-menu-end mt-3" aria-labelledby="dropdownMenuProfile">
                                        <li>
                                            <a className="dropdown-item" href="#!">
                                                <i className="material-icons leading-icon">person</i>
                                                <div className="me-3">Profile</div>
                                            </a>
                                        </li>
                                        <li>
                                            <a className="dropdown-item" href="#!">
                                                <i className="material-icons leading-icon">settings</i>
                                                <div className="me-3">Settings</div>
                                            </a>
                                        </li>
                                        <li>
                                            <a className="dropdown-item" href="#!">
                                                <i className="material-icons leading-icon">help</i>
                                                <div className="me-3">Help</div>
                                            </a>
                                        </li>
                                        <li><hr className="dropdown-divider" /></li>
                                        <li>
                                            <a className="dropdown-item" href="#!">
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
 
    );
}
export default Header;