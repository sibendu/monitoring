import Footer from "../common/Footer";

const Settings = () => {

  return ( 
    <div id="layoutDrawer_content">
    
    <main>
        
        <div className="container-xl p-5">
            <div className="row justify-content-between align-items-center mb-5">
                <div className="col flex-shrink-0 mb-5 mb-md-0">
                    <h1 className="display-4 mb-0">Account Settings</h1>
                    <div className="text-muted">Settings of tenant</div>
                </div>
            </div>
          
            <div className="row gx-5">
       
                <div className="col-xl-6 mb-5">
                    <div className="card card-raised h-100">
                        <div className="card-body p-4">
                            <div className="d-flex justify-content-between">
                                <div className="me-4">
                                    <h2 className="card-title mb-0">Privacy Suggestions</h2>
                                    <p className="card-text">Take our privacy checkup to choose which settings are right for you.</p>
                                </div>
                            </div>
                        </div>
                        <div className="card-footer bg-transparent position-relative ripple-gray px-4"><a className="stretched-link text-decoration-none" href="#!">Review suggestions (4)</a></div>
                    </div>
                </div>

                <div className="col-xl-6 mb-5">
                    <div className="card card-raised h-100">
                        <div className="card-body p-4">
                            <div className="d-flex justify-content-between">
                                <div className="me-4">
                                    <h2 className="card-title mb-0">Account Storage</h2>
                                    <p className="card-text">Your account storage is shared across all devices.</p>
                                    <div className="progress mb-2" ><div className="progress-bar" role="progressbar" aria-valuenow="10" aria-valuemin="0" aria-valuemax="30"></div></div>
                                    <div className="card-text">10 GB of 30 GB used</div>
                                </div>
                            </div>
                        </div>
                        <div className="card-footer bg-transparent position-relative ripple-gray px-4"><a className="stretched-link text-decoration-none" href="#!">Manage storage</a></div>
                    </div>
                </div>
            </div>
            
        </div>
    </main>

    <Footer/>
    
</div>

);

};
  
export default Settings;