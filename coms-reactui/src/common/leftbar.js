import React , { useState ,useEffect } from 'react';
import ReactDOM from 'react-dom';

import MenuItem from './MenuItem';

function LeftBar(props) {

    const [userroles, setUserroles] = useState([]);

    const allMenuItems = [
        {'id':'Home','title':'Dashboard', 'icon': 'fas fa-home', 'roles':['ADMIN','PROCESS_OWNER','EMPLOYEE']},
        {'id':'Task','title':'Task', 'icon': 'fas fa-briefcase', 'roles':['ADMIN','PROCESS_OWNER','EMPLOYEE','SUPERADMIN'],
            'subItems': [ 
                {'id':'NewTask', 'title': 'New Task'}  ,
                {'id':'SearchTask', 'title': 'Search Task'}  ,
                {'id':'SampleTask', 'title': 'Sample Form'},  
                {'id':'ListTask', 'title': 'Tasks List'}  
            ]},
        {'id':'Customer', 'title':'Customer','icon': 'fas fa-briefcase',
            'subItems': [
                {'id':'AddCustomer', 'title': 'Add Customer','roles':['ADMIN','PROCESS_OWNER']},
                {'id':'SearchCustomer', 'title': 'Search Customer','roles':['ADMIN','PROCESS_OWNER','EMPLOYEE']}   
            ]},
        {'id':'Preferences','title':'Preferences',  'icon': 'fas fa-circle','roles':['ADMIN','PROCESS_OWNER','EMPLOYEE']}, 
        {'id':'Settings','title':'Account Settings',  'icon': 'fas fa-user','roles':['SUPERADMIN']},               
        {'id':'Sample','title':'Sample Page', 'icon': 'fas fa-home', 'roles':['ADMIN','PROCESS_OWNER','EMPLOYEE']}        
    ];

    const menuItems = allMenuItems;

    useEffect(() => { 

        if(props.user.roles!== undefined) 
        {
            setUserroles(props.user.roles);
        }         
    }); 

    const handleClick = (page)=> {
        props.onClick(page);
    } 

    return (
        <div id="layoutDrawer_nav">
               
                <nav className="drawer accordion drawer-light bg-white" id="drawerAccordion">
                    <div className="drawer-menu">
                        <div className="nav">
                           
                            <div className="drawer-menu-divider d-sm-none"></div>
                           
                            <div className="drawer-menu-heading"></div>

                            {
                                menuItems.map(item => {
                                    return (  
                                        <MenuItem key={item.id} item={item} handleClick={handleClick}/>
                                  )  
                                })
                            }            

                           
                        </div>
                    </div>
                
                </nav>
            </div>
    )
}
export default LeftBar;