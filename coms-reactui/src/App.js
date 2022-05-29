import React , { useState ,useEffect } from 'react';

import Axios from 'axios'; 
import Header from './common/Header';
import LeftBar from './common/Leftbar';
import EntityAllView from './pages/entity_all_view';
import EntitySingleView from './pages/entity_single_view';
import EntityCreateForm from './pages/entity_create_form';
import EntityEditForm from './pages/entity_single_edit';
import LoginForm from './pages/LoginForm';
import Home from './pages/Home';
import * as configClass from './common/config';

function App() {
    const [page, setPage] = useState("Home");
    const [user, setUser] = useState({});

    const [bearertoken, setBearertoken] = useState("");
    const [entityid, setEntityid] = useState(0);
    
    useEffect(() => { 
        console.log("App called");
    }, [page]);

    const handlePageNavigationLinkClick = (page) => {
        console.log("page:"+ page);
        setPage(page);	
      }
      const handleViewClick = (page,entid) => {
        console.log("page:"+ page);
        setPage(page);
        setEntityid(entid);
      }
      const handleEditClick = (page,entid) => {
        console.log("page:"+ page);
        setPage(page);
        setEntityid(entid);
      }
      const handleLoginsuccess =(token, user) =>
      {
        console.log("token: "+ token+ " ; user: "+user);
        setBearertoken(token);
	setUser(user);
	setPage("Home");
      }

  if(!user){
	return (
        	<div id="layoutDrawer">
			<LoginForm handleLoginsuccess={handleLoginsuccess} handleRegisterClick={handlePageNavigationLinkClick}/>
		</div>
  	);
  }
	
  return (
        <div id="layoutDrawer">
          
          <LeftBar user={user} onClick={handlePageNavigationLinkClick}/>

          <Header onClick={handlePageNavigationLinkClick}/>

          {

            page==="Home"?<Home Token={bearertoken}/>:
            page==="Login"?<LoginForm handleLoginsuccess={handleLoginsuccess} handleRegisterClick={handlePageNavigationLinkClick}/>:
            page==="CustomerAllView"?<EntityAllView Formprops={configClass.entity_all_view.view_all_customer} handleViewClick={handleViewClick} handleEditClick={handleEditClick} handleCreateClick={handlePageNavigationLinkClick}/>:
            page==="ProcessDefintionAllView"?<EntityAllView Formprops={configClass.entity_all_view.view_all_process_definition} handleViewClick={handleViewClick} handleEditClick={handleEditClick} handleCreateClick={handlePageNavigationLinkClick}/>:
            page==="SingleCustomerView"?<EntitySingleView Entityid={entityid} Formprops={configClass.entity_single_view.view_single_customer} handleEditClick={handleEditClick}/>:
            page==="SingleProcessDefinitionView"?<EntitySingleView Entityid={entityid} Formprops={configClass.entity_single_view.view_single_process_defintion} handleEditClick={handleEditClick}/>:
            page==="EntityCreateForm"?<EntityCreateForm Formprops={configClass.entity_create.create_customer}/>:
            page==="EditCustomer"?<EntityEditForm Formprops={configClass.entity_edit.edit_customer} Entityid={entityid}/>:
            page==="RegisterUser"?<EntityCreateForm Formprops={configClass.entity_create.register_user}/>:<Home/>

          }

      </div>
  );


}

export default App;
