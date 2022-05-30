import React from 'react';

export default function MenuItem(props) {

    const {item} = props;
    
    const dataBsTarget = { 'data-bs-target': '#collapse'+item.id};
    const ariaControls = { 'aria-controls': 'collapse'+item.id};
    const submenuId = { 'id': 'collapse'+item.id};
    
    if(item.subItems){
        return (        
            <>
        
        <a className="nav-link collapsed" href="#" data-bs-toggle="collapse" {...dataBsTarget} aria-expanded="false" {...ariaControls}>
            <div className="nav-link-icon"><i className="material-icons">view_compact</i></div>
            {item.title}
            <div className="drawer-collapse-arrow"><i className="material-icons">expand_more</i></div>
        </a>
        
        <div className="collapse" {...submenuId} aria-labelledby="headingOne" data-bs-parent="#drawerAccordion">
            <nav className="drawer-menu-nested nav">

            {
                item.subItems.map(submenu => {
                    return(                                
                        <a className="nav-link" key={submenu.id} onClick={()=> props.handleClick(submenu.id)}>{submenu.title}</a>
                    );
                })
            }
                
            </nav>
        </div>

            </>
        );
    }else{
        return (  
            <a className="nav-link" href="#" onClick={()=> props.handleClick(item.id)}>
                <div className="nav-link-icon"><i className="material-icons">language</i></div>
                {item.title}
            </a>      
        );    
    }    			
 }