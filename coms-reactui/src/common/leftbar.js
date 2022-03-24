import React , { useState ,useEffect } from 'react';
import ReactDOM from 'react-dom';
function LeftBar(props) {
    const [userroles, setUserroles] = useState([]);
    useEffect(() => { 
        //console.log("leftmenu useeffect called"); 
        if(props.Loggeduser.roles!== undefined) 
        {
            setUserroles(props.Loggeduser.roles);
        }      
        
    }, [props.Loginstatus]); 
    const handleClick = (page)=> {
        props.onClick(page);
      } 
    return (
        <nav id="sidebar">
        <div className="sidebar-header">
            <h3>COMS</h3>
        </div>

        <ul className="list-unstyled components">
            <li className="active">
                <a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" className="dropdown-toggle">
                    <i className="fas fa-home"></i>
                    Home
                </a>
                <ul className="collapse list-unstyled" id="homeSubmenu">
                    <li>
                        <a href="#" onClick={()=>handleClick("Home")}>Home</a>
                    </li>
                    <li>
                        <a href="#" onClick={()=>handleClick("EntityAllView")}>Customer</a>
                    </li>
                </ul>
            </li>
            {
                ((userroles.includes("admin")||userroles.includes("superadmin")) &&
                    <li>
                    <a href="#">
                        <i className="fas fa-cog"></i>
                        Admin
                    </a>
                </li>
                )
            }
            <li>
                <a href="#">
                    <i className="fas fa-briefcase"></i>
                    About
                </a>
            </li>
            <li>
                <a href="#">
                    <i className="fas fa-paper-plane"></i>
                    Contact
                </a>
            </li>
        </ul>

    </nav>
    )
}
export default LeftBar;