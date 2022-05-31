import React from 'react';
function PageHeader(props) {
    
    return (
                <div className="row justify-content-between align-items-center mb-1">
                    <div className="col flex-shrink-0 mb-2 mb-md-0">
                        <h1 className="display-4 mb-0">{props.heading}</h1>
                        <div className="text-muted">{props.subheading}</div>
                    </div>
                </div>                          
    );
}
export default PageHeader;