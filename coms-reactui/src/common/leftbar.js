import React , { useState ,useEffect } from 'react';
import ReactDOM from 'react-dom';

function LeftBar(props) {

    const [userroles, setUserroles] = useState([]);

    useEffect(() => { 

        if(props.user.roles!== undefined) 
        {
            setUserroles(props.user.roles);
        }      
        
    }, [props.Loginstatus]); 

    const handleClick = (page)=> {
        props.onClick(page);
    } 

    return (
        <div id="layoutDrawer_nav">
               
                <nav className="drawer accordion drawer-light bg-white" id="drawerAccordion">
                    <div className="drawer-menu">
                        <div className="nav">
                           
                            <div className="drawer-menu-divider d-sm-none"></div>
                           
                            <div className="drawer-menu-heading">Interface</div>
                           
                            <a className="nav-link" href="#" onClick={()=>handleClick("Home")}>
                                <div className="nav-link-icon"><i className="material-icons">language</i></div>
                                Home
                            </a>
                            
                            <a className="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseDashboards" aria-expanded="false" aria-controls="collapseDashboards">
                                <div className="nav-link-icon"><i className="material-icons">dashboard</i></div>
                                Customer (test purpose)
                                <div className="drawer-collapse-arrow"><i className="material-icons">expand_more</i></div>
                            </a>
                            
                            <div className="collapse" id="collapseDashboards" aria-labelledby="headingOne" data-bs-parent="#drawerAccordion">
                                <nav className="drawer-menu-nested nav">
                                    <a href="#" className="nav-link" onClick={()=>handleClick("CustomerAllView")}>Customer</a>
                                </nav>
                            </div>
                           
                            <a className="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div className="nav-link-icon"><i className="material-icons">view_compact</i></div>
                                Dashboard
                                <div className="drawer-collapse-arrow"><i className="material-icons">expand_more</i></div>
                            </a>
                           
                            <div className="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#drawerAccordion">
                                <nav className="drawer-menu-nested nav">
                                    <a className="nav-link" href="layout-dark.html">My tasks</a>
                                    <a className="nav-link" href="layout-light.html">Menu 2</a>
                                    <a className="nav-link" href="layout-static.html">Menu 3</a>
                                </nav>
                            </div>
                            {
                ((userroles.includes("admin")||userroles.includes("superadmin")) &&
                            <div>
                            <div className="drawer-menu-divider"></div>
                            
                            <div className="drawer-menu-heading">Admin</div>
                            
                            
                           
                            
                            <a className="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseForms" aria-expanded="false" aria-controls="collapseForms">
                                <div className="nav-link-icon"><i className="material-icons">description</i></div>
                                BPM
                                <div className="drawer-collapse-arrow"><i className="material-icons">expand_more</i></div>
                            </a>
                           
                            <div className="collapse" id="collapseForms" aria-labelledby="headingOne" data-bs-parent="#drawerAccordion">
                                <nav className="drawer-menu-nested nav">
                                    <a className="nav-link" href="#" onClick={()=>handleClick("ProcessDefintionAllView")}>Process Definition</a>
                                    <a className="nav-link" href="forms-checks-and-radios.html">Menu 2</a>
                                   
                                </nav>
                            </div>
                            
                            <a className="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseUtilities" aria-expanded="false" aria-controls="collapseUtilities">
                                <div className="nav-link-icon"><i className="material-icons">build</i></div>
                                Utilities
                                <div className="drawer-collapse-arrow"><i className="material-icons">expand_more</i></div>
                            </a>
                            
                            <div className="collapse" id="collapseUtilities" aria-labelledby="headingOne" data-bs-parent="#drawerAccordion">
                                <nav className="drawer-menu-nested nav">
                                    <a className="nav-link" href="utilities-background.html">Menu 1</a>
                                    <a className="nav-link" href="utilities-borders.html">Menu 2</a>
                                 
                                </nav>
                            </div>
                            
                            </div>
                )}
                        </div>
                    </div>
                    
                    <div className="drawer-footer border-top">
                        <div className="d-flex align-items-center">
                            <i className="material-icons text-muted">account_circle</i>
                            <div className="ms-3">
                                <div className="caption">Logged in as:</div>
                                <div className="small fw-500">Start Bootstrap</div>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
    )
}
export default LeftBar;