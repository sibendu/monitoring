import React from 'react';

export default function SubMenu(props) {

    return (
        <li>
          <a href="#" onClick={()=> props.handleClick(props.menu.id)}>{props.menu.title}</a>
        </li>
    );		
 }