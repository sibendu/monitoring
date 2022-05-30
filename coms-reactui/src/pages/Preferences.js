import Footer from "../common/Footer";

const Preferences = () => {
    return (

      <div id="layoutDrawer_content">
        
        <main>
            <div className="container-xl p-5">
                <div className="row justify-content-between align-items-center mb-5">
                    <div className="col flex-shrink-0 mb-5 mb-md-0">
                        <h1 className="display-4 mb-0">Preferences</h1>
                        <div className="text-muted">Set your preferences</div>
                    </div>
                </div>
            </div>
        </main>

        <Footer/>
      </div>  
    );
  };
  
export default Preferences;