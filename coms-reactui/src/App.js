import React , { useState ,useEffect } from 'react';
import Axios from 'axios'; 

import Header from './common/Header';
import LeftBar from './common/Leftbar';
import Footer from './common/Footer';

import LoginForm from './pages/LoginForm';
import Home from './pages/Home';

import AddCustomer from './pages/customer/AddCustomer';
import SearchCustomer from './pages/customer/SearchCustomer';

import Settings from './pages/Settings';
import Preferences from './pages/Preferences';
import Profile from './pages/Profile';

import NewTask from './pages/task/NewTask';
import SearchTask from './pages/task/SearchTask'
import SampleTask from './pages/task/SampleTask';
import ListTask from './pages/task/ListTask';

import NewProcess from './pages/process/NewProcess';
import SearchProcess from './pages/process/SearchProcess'

import NewSample from './pages/sample/NewSample';
import SearchSample from './pages/sample/SearchSample'

function App() {
    const [page, setPage] = useState("Home");
    const [user, setUser] = useState({});
    const [pageDataObject, setPageDataObject] = useState({});

    const [token, setToken] = useState("");
    const [entityid, setEntityid] = useState(0);
    
    useEffect(() => { 
       console.log("App called");
    }, [page]);

    const handlePageNavigationLinkClick = (page, pageDoB) => {
      //console.log('Clicked = ' + page);
      //console.log(pageDoB);
      setPage(page);	
      setPageDataObject(pageDoB);
    }
    
    const logout = () => {
      //console.log("Logout");
      setUser(null);
      setToken(null);
    }

    const handleLoginsuccess =(token, user) =>
    {
        //console.log("token: "+ token+ " ; user: "+user);
        setToken(token);
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
          
          <Header user={user} onClick={handlePageNavigationLinkClick} onLogout={logout} />

          <LeftBar user={user} onClick={handlePageNavigationLinkClick}/>
          
          {

              page==="Home"?<Home user={user}/>:
                              
              page==="NewTask"?<NewTask user={user}/>:

              page==="SearchTask"?<SearchTask user={user}/>:
             
              page==="SampleTask"?<SampleTask user={user}/>: 

              page==="ListTask"?<ListTask user={user}/>: 

              page==="AddCustomer"?<AddCustomer user={user}/>:

              page==="SearchCustomer"?<SearchCustomer user={user}/>:

              page==="Settings"?<Settings user={user}/>:

              page==="Preferences"?<Preferences user={user}/>:

              page==="Profile"?<Profile user={user}/>:

              page==="NewProcess"?<NewProcess user={user} onClick={handlePageNavigationLinkClick} pageDataObject={pageDataObject}/>:

              page==="SearchProcess"?<SearchProcess user={user} onClick={handlePageNavigationLinkClick} pageDataObject={pageDataObject}/>:

              page==="NewSample"?<NewSample user={user} onClick={handlePageNavigationLinkClick} pageDataObject={pageDataObject}/>:

              page==="SearchSample"?<SearchSample user={user} onClick={handlePageNavigationLinkClick} pageDataObject={pageDataObject}/>:              

              <Home user={user}/>
          }

      </div>
  );

}

export default App;
