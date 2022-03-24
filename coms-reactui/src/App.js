import React , { useState ,useEffect } from 'react';
import ReactDOM from 'react-dom';
import Axios from 'axios'; 
import logo from './logo.svg';
import './style.css';
import Header from './common/header';
import LeftBar from './common/leftbar';
import EntityAllView from './pages/entity_all_view';
import EntitySingleView from './pages/entity_single_view';
import EntityCreateForm from './pages/entity_create_form';
import EntityEditForm from './pages/entity_single_edit';
import LoginForm from './pages/login_form';
import Home from './pages/home';
import * as configClass from './common/config';
function App() {
    const [page, setPage] = useState("Home");
    const [entityid, setEntityid] = useState(0);
    const [bearertoken, setBearertoken] = useState("");
    const [loggeduser, setLoggeduser] = useState({});
    const [loginstatus, setLoginstatus] = useState(false);
    useEffect(() => { 
        console.log("App called");
    }, [page]);
    const handlePageNavigationLinkClick = (page) => {
        console.log("page2:"+ page);
        setPage(page);
      }
      const handleViewClick = (page,entid) => {
        console.log("page2:"+ page);
        setPage(page);
        setEntityid(entid);
      }
      const handleEditClick = (page,entid) => {
        console.log("page2:"+ page);
        setPage(page);
        setEntityid(entid);
      }
      const handleLoginsuccess =(token, useremail) =>
      {
        console.log("token:"+ token);
        setBearertoken(token);
        let headers = { "Authorization": 'Bearer ' + token};
        Axios.get('/getuser',{
                  headers: headers
            })
            .then(response => 
            {
                console.log(response.data);
                setLoggeduser(response.data);
                setPage("Home");
                setLoginstatus(true);
            });     
      }
  return (
    <div className="wrapper">
    <LeftBar Loginstatus={loginstatus} Loggeduser={loggeduser} onClick={handlePageNavigationLinkClick}/>
    <div id="content">
        <Header Loginstatus={loginstatus} Useremail={loggeduser.first_name} onClick={handlePageNavigationLinkClick}/>
        {
          page==="Home"?<Home Token={bearertoken}/>:
          page==="Login"?<LoginForm handleLoginsuccess={handleLoginsuccess} handleRegisterClick={handlePageNavigationLinkClick}/>:
          page==="EntityAllView"?<EntityAllView Formprops={configClass.entity_all_view.view_all_customer} handleViewClick={handleViewClick} handleEditClick={handleEditClick} handleCreateClick={handlePageNavigationLinkClick}/>:
          page==="SingleCustomerView"?<EntitySingleView Entityid={entityid} Formprops={configClass.entity_single_view.view_single_customer} handleEditClick={handleEditClick}/>:
          page==="EntityCreateForm"?<EntityCreateForm Formprops={configClass.entity_create.create_customer}/>:
          page==="EditForm"?<EntityEditForm Formprops={configClass.entity_edit.edit_customer} Entityid={entityid}/>:
          page==="RegisterUser"?<EntityCreateForm Formprops={configClass.entity_create.register_user}/>:<Home/>
        }
    </div>
</div>
  );
}

export default App;
