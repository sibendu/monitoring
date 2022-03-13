import React from 'react';
import SubMenu from './SubMenu';

export default function MenuItem(props) {

    const {item} = props;
    const menuHref = { 'href': '#'+item.id};
    const menuId = { 'id': item.id};
    const icon = { 'className': item.icon};

    if(item.subItems){
        return (        
            <>
            <li>
                <a {...menuHref} data-toggle="collapse" aria-expanded="false" className="dropdown-toggle">
                    <i {...icon}></i>{item.title}
                </a>
                <ul className="collapse list-unstyled" {...menuId}>
                    {
                            item.subItems.map(menu => {
                                return(
                                    <SubMenu key={menu.id} menu={menu} handleClick={props.handleClick}/>    
                                );
                            })
                    }
                </ul>
            </li>
            </>
        );
    }else{
        return (  
            <li>
                <a href="#" onClick={()=> props.handleClick(item.id)}>
                    <i {...icon}></i>{item.title}
                </a>
            </li>      
        );    
    }    			
 }