import React , { useState ,useEffect } from 'react';
import ReactDOM from 'react-dom';
import Axios from 'axios'; 
import Header from '../../common/Header';
const EntityEditForm = (props) => {
    const [formData, setFormData] = useState({});
    const [fieldData, setFieldData] = useState([]);
      useEffect(() => {          
        console.log("useEffect-edit form called");
        setFieldData(props.Formprops.field_data);
        const requestbody = {
            service:props.Formprops.service,    
            operation:props.Formprops.dataload_operation + props.Entityid,
                requesttype:'get',
                requestbody:''
            };
            Axios.post('/invoke', requestbody)
                  .then(response => 
                    {   
                        setFormData(response.data);
                    }
                ); 
          
    }, [props.Entityid]); 
    const onSubmit = e => {
        e.preventDefault();
        const requestbody = {
            service:props.Formprops.service,    
            operation: props.Formprops.submit_operation,
                requesttype:'post',
                requestbody:JSON.stringify(formData)
            };
        Axios.post('/invoke', requestbody)
        .then(res=>{
        console.log(res);
        console.log(res.data);
        alert(res.data.status);
        });
    };
    const handleChange = e =>{
        formData[e.target.name] = e.target.value;
        setFormData({ ...formData });
        //setFormData(currentValues => {
        //    currentValues[e.target.name] = e.target.value;
        //    return currentValues;
        //});
        //({target:{name,value}}) => setFormData(state => ({ ...formData, [name]:value }), [])
      };
    return (
        <React.Fragment>
              <div id="layoutDrawer_content">
        
        <main>
        <div class="container-xl p-5">
            <h3>{props.Formprops.form_title}</h3>
            <form class="form-horizontal" onSubmit={onSubmit}>
                {formData && fieldData.map(field => {  
                    switch (field.field_type) {
                        case "text":
                                return (
                                    <div class="form-group">
                                    <label for={field.field_name} class="col-xs-2">{field.field_label}</label>
                                    <div class="col-xs-10">
                                    <input type="text" class="form-control" name={field.field_name} key={field.field_name} value={formData[field.field_name]} onChange={handleChange} />
                                    </div>
                                </div>
                                )
                         case "label":
                                return (
                                    <div class="form-group">
                                    <label for={field.field_name} class="col-xs-2">{field.field_label}</label>
                                    <div class="col-xs-10">{formData[field.field_name] }</div>
                                </div>
                                )
                    }
                })
                }
                <div class="col-xs-10 col-xs-offset-2">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
            </div>
            </main>
            </div>
            </React.Fragment>

    );
};
export default EntityEditForm;