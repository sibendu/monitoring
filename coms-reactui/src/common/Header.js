import { BsGear, BsFillArrowRightSquareFill } from "react-icons/bs";

function logout(){
	alert('logout');
}

export default function Header(props) {
    const {user} = props;
    return (
        <nav id="topbar">
            <div className="topbar-header" align="right">
                Welcome {user.name} <BsGear onClick={() => alert('Setting')}/> <BsFillArrowRightSquareFill onClick={() => logout()}/>         
            </div>
        </nav>
    );
}