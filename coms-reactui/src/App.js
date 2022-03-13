import React , { useState } from 'react';
import './style.css';

import Header from './common/Header';
import LeftMenu from './common/LeftMenu';
import Footer from './common/Footer';
import SignIn from './common/SignIn';

import Home from './pages/Home';
import AddCustomer from './pages/AddCustomer';
import SearchCustomer from './pages/SearchCustomer';
import AddProduct from './pages/AddProduct';
import SearchProduct from './pages/SearchProduct';
import NewTask from './pages/NewTask';
import Preferences from './pages/Preferences';
import Settings from './pages/Settings';

function App() {

  const [page, setPage] = useState("Home");
  const [user, setUser] = useState();
	
  if(!user) {
    return (
      <div className="wrapper">
      <SignIn setUser={setUser} />
      </div>
    );  
  }

  const handlePageNavigation = (page) => {
    setPage(page);
  }
      
  return (
    <div className="wrapper">

      <Header user={user} /> 

      <div className="wrapper clearfix">
            <LeftMenu onClick={handlePageNavigation} user={user}/>
            <div className="section">
                {
                page==="Home"?<Home user={user}/>:
                
                page==="NewTask"?<NewTask user={user}/>:

                page==="AddCustomer"?<AddCustomer user={user}/>:

                page==="SearchCustomer"?<SearchCustomer user={user}/>:

                page==="AddProduct"?<AddProduct user={user}/>:

                page==="SearchProduct"?<SearchProduct user={user}/>:
                
                page==="Settings"?<Settings user={user}/>:

                page==="Preferences"?<Preferences user={user}/>:

                <Home user={user}/>

                }  
            </div> 
            
        </div>
        <div class="footer">
          <Footer sx={{ mt: 8, mb: 4 }} />
        </div>        
  </div>
  );	
}
export default App;
