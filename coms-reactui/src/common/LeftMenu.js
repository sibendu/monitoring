import React from 'react';
import MenuItem from './MenuItem';

import '../style.css';

export default function LeftMenu(props) {
    
    const filterMenuByRole = (user)=> {
        
        //For the Menu Items, Define 'roles' which can access that item
        // If Role access defined at the parent menu level, it is ignored at children level
        // If Parent menu item does not have any role defined, child items are checked   
        const allMenuItems = [
            {'id':'Home','title':'Dashboard', 'href': 'Home', 'icon': 'fas fa-home', 'roles':['ADMIN','PROCESS_OWNER','EMPLOYEE']},
            {'id':'Task','title':'Task', 'icon': 'fas fa-briefcase', 'roles':['ADMIN','PROCESS_OWNER','EMPLOYEE'],
                'subItems': [ 
                    {'id':'NewTask', 'title': 'New Task'}  
                ]},
            {'id':'Customer', 'title':'Customer','icon': 'fas fa-briefcase',
                'subItems': [
                    {'id':'AddCustomer', 'title': 'Add Customer','roles':['ADMIN','PROCESS_OWNER']},
                    {'id':'SearchCustomer', 'title': 'Search Customer','roles':['ADMIN','PROCESS_OWNER','EMPLOYEE']}   
                ]},
            {'id':'Product', 'title':'Product','icon': 'fas fa-briefcase',
                'subItems': [
                    {'id':'AddProduct', 'title': 'Add Product','roles':['ADMIN']},
                    {'id':'SearchProduct', 'title': 'Search Product','roles':['ADMIN','PROCESS_OWNER','EMPLOYEE']}   
                ]},
            {'id':'Preferences','title':'Preferences', 'href': 'Preferences', 'icon': 'fas fa-circle','roles':['ADMIN','PROCESS_OWNER','EMPLOYEE']}, 
            {'id':'Settings','title':'Account Settings', 'href': 'Settings', 'icon': 'fas fa-user','roles':['SUPERADMIN']},               
        ];

        if(user.roles.includes('SUPERADMIN')){
            return allMenuItems;    
        }

        const filteredMenu = [];

        allMenuItems.map(item => {
            if(item.roles){
                let isFound = user.roles.some( ai => item.roles.includes(ai) );
                if(isFound){
                    filteredMenu.push(item);     
                }
            }else{
                if(item.href){ //No child
                    filteredMenu.push(item);
                }else{//Check for each children if current role should see them
                    let menuItem = {'id': item.id, 'title': item.title, 'icon': item.icon, 'subItems': []};
                    item.subItems.map(menu => {
                        let isFound = user.roles.some( ai => menu.roles.includes(ai) );
                        if(isFound){
                            menuItem.subItems.push(menu);
                        }
                    });    
                    if(menuItem.subItems.length > 0){
                        filteredMenu.push(menuItem); 
                    }    
                }
            }
        });

        return filteredMenu;
    }

    const handleClick = (page)=> {
        //alert('page clicked:'+ page);
        props.onClick(page);
    }

    const menuItems = filterMenuByRole(props.user);

    return (
        <nav id="sidebar">
        <div className="sidebar-header">
            <ul className="list-unstyled components">
                {
                    menuItems.map(item => {
                        return (     
                            <MenuItem key={item.id} item={item} handleClick={handleClick}/>
                        )
                    })    
                }

            </ul>            
        </div>
        </nav>
    )
}