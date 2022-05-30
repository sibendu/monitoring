import React from 'react';

import Footer from "../common/Footer";

function Home(props) {

    return ( 
        <div id="layoutDrawer_content">
        
        <main>
            
            <div className="container-xl p-5">
                <div className="row justify-content-between align-items-center mb-5">
                    <div className="col flex-shrink-0 mb-5 mb-md-0">
                        <h1 className="display-4 mb-0">Dashboard</h1>
                        <div className="text-muted">Sales overview &amp; summary</div>
                    </div>
                </div>
              
                <div className="row gx-5">
                    <div className="col-xxl-3 col-md-6 mb-5">
                        <div className="card card-raised bg-primary text-white">
                            <div className="card-body px-4">
                                <div className="d-flex justify-content-between align-items-center mb-2">
                                    <div className="me-2">
                                        <div className="display-5 text-white">101.1K</div>
                                        <div className="card-text">Downloads</div>
                                    </div>
                                    <div className="icon-circle bg-white-50 text-primary"><i className="material-icons">download</i></div>
                                </div>
                                <div className="card-text">
                                    <div className="d-inline-flex align-items-center">
                                        <i className="material-icons icon-xs">arrow_upward</i>
                                        <div className="caption fw-500 me-2">3%</div>
                                        <div className="caption">from last month</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-xxl-3 col-md-6 mb-5">
                        <div className="card card-raised bg-warning text-white">
                            <div className="card-body px-4">
                                <div className="d-flex justify-content-between align-items-center mb-2">
                                    <div className="me-2">
                                        <div className="display-5 text-white">12.2K</div>
                                        <div className="card-text">Purchases</div>
                                    </div>
                                    <div className="icon-circle bg-white-50 text-warning"><i className="material-icons">storefront</i></div>
                                </div>
                                <div className="card-text">
                                    <div className="d-inline-flex align-items-center">
                                        <i className="material-icons icon-xs">arrow_upward</i>
                                        <div className="caption fw-500 me-2">3%</div>
                                        <div className="caption">from last month</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-xxl-3 col-md-6 mb-5">
                        <div className="card card-raised bg-secondary text-white">
                            <div className="card-body px-4">
                                <div className="d-flex justify-content-between align-items-center mb-2">
                                    <div className="me-2">
                                        <div className="display-5 text-white">5.3K</div>
                                        <div className="card-text">Customers</div>
                                    </div>
                                    <div className="icon-circle bg-white-50 text-secondary"><i className="material-icons">people</i></div>
                                </div>
                                <div className="card-text">
                                    <div className="d-inline-flex align-items-center">
                                        <i className="material-icons icon-xs">arrow_upward</i>
                                        <div className="caption fw-500 me-2">3%</div>
                                        <div className="caption">from last month</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-xxl-3 col-md-6 mb-5">
                        <div className="card card-raised bg-info text-white">
                            <div className="card-body px-4">
                                <div className="d-flex justify-content-between align-items-center mb-2">
                                    <div className="me-2">
                                        <div className="display-5 text-white">7</div>
                                        <div className="card-text">Channels</div>
                                    </div>
                                    <div className="icon-circle bg-white-50 text-info"><i className="material-icons">devices</i></div>
                                </div>
                                <div className="card-text">
                                    <div className="d-inline-flex align-items-center">
                                        <i className="material-icons icon-xs">arrow_upward</i>
                                        <div className="caption fw-500 me-2">3%</div>
                                        <div className="caption">from last month</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row gx-5">
                   
                    <div className="col-lg-8 mb-5">
                        <div className="card card-raised h-100">
                            <div className="card-header bg-primary text-white px-4">
                                <div className="d-flex justify-content-between align-items-center">
                                    <div className="me-4">
                                        <h2 className="card-title text-white mb-0">Revenue Breakdown</h2>
                                        <div className="card-subtitle">Compared to previous year</div>
                                    </div>
                                    <div className="d-flex gap-2 me-n2">
                                        <button className="btn btn-lg btn-text-white btn-icon" type="button"><i className="material-icons">download</i></button>
                                        <button className="btn btn-lg btn-text-white btn-icon" type="button"><i className="material-icons">print</i></button>
                                    </div>
                                </div>
                            </div>
                            <div className="card-body p-4">
                                <div className="row gx-4">
                                    <div className="col-12 col-xxl-2">
                                        <div className="d-flex flex-column flex-md-row flex-xxl-column align-items-center align-items-xl-start justify-content-between">
                                            <div className="mb-4 text-center text-md-start">
                                                <div className="text-xs font-monospace text-muted mb-1">Actual Revenue</div>
                                                <div className="display-5 fw-500">$59,482</div>
                                            </div>
                                            <div className="mb-4 text-center text-md-start">
                                                <div className="text-xs font-monospace text-muted mb-1">Revenue Target</div>
                                                <div className="display-5 fw-500">$50,000</div>
                                            </div>
                                            <div className="mb-4 text-center text-md-start">
                                                <div className="text-xs font-monospace text-muted mb-1">Goal</div>
                                                <div className="display-5 fw-500 text-success">119%</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="col-12 col-xxl-10"><canvas id="dashboardBarChart"></canvas></div>
                                </div>
                            </div>
                            <div className="card-footer bg-transparent position-relative ripple-gray">
                                <a className="d-flex align-items-center justify-content-end text-decoration-none stretched-link text-primary" href="#!">
                                    <div className="fst-button">Open Report</div>
                                    <i className="material-icons icon-sm ms-1">chevron_right</i>
                                </a>
                            </div>
                        </div>
                    </div>
             
                    <div className="col-lg-4 mb-5">
                        <div className="card card-raised h-100">
                            <div className="card-header bg-primary text-white px-4">
                                <div className="d-flex justify-content-between align-items-center">
                                    <div className="me-4">
                                        <h2 className="card-title text-white mb-0">Segments</h2>
                                        <div className="card-subtitle">Revenue sources</div>
                                    </div>
                                    <div className="dropdown">
                                        <button className="btn btn-lg btn-text-light btn-icon me-n2 dropdown-toggle" id="segmentsDropdownButton" type="button" data-bs-toggle="dropdown" aria-expanded="false"><i className="material-icons">more_vert</i></button>
                                        <ul className="dropdown-menu" aria-labelledby="segmentsDropdownButton">
                                            <li><a className="dropdown-item" href="#!">Action</a></li>
                                            <li><a className="dropdown-item" href="#!">Another action</a></li>
                                            <li><a className="dropdown-item" href="#!">Something else here</a></li>
                                            <li><hr className="dropdown-divider" /></li>
                                            <li><a className="dropdown-item" href="#!">Separated link</a></li>
                                            <li><a className="dropdown-item" href="#!">Separated link</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div className="card-body p-4">
                                <div className="d-flex h-100 w-100 align-items-center justify-content-center">
                                    <div className="w-100"><canvas id="myPieChart"></canvas></div>
                                </div>
                            </div>
                            <div className="card-footer bg-transparent position-relative ripple-gray">
                                <a className="d-flex align-items-center justify-content-end text-decoration-none stretched-link text-primary" href="#!">
                                    <div className="fst-button">Open Report</div>
                                    <i className="material-icons icon-sm ms-1">chevron_right</i>
                                </a>
                            </div>
                        </div>
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
                <div className="card card-raised">
                    <div className="card-header bg-primary text-white px-4">
                        <div className="d-flex justify-content-between align-items-center">
                            <div className="me-4">
                                <h2 className="card-title text-white mb-0">Orders</h2>
                                <div className="card-subtitle">Details and history</div>
                            </div>
                            <div className="d-flex gap-2">
                                <button className="btn btn-lg btn-text-white btn-icon" type="button"><i className="material-icons">download</i></button>
                                <button className="btn btn-lg btn-text-white btn-icon" type="button"><i className="material-icons">print</i></button>
                            </div>
                        </div>
                    </div>
                    <div className="card-body p-4">

                       
                    </div>
                </div>
            </div>
        </main>

        <Footer/>
    </div>

    );
}
export default Home;