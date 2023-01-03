import React , { useState , useEffect } from 'react';

import Footer from "../../common/Footer";
import PageHeader from '../../common/PageHeader';

import axios from "axios";

import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import Divider from '@mui/material/Divider';
import Chip from '@mui/material/Chip';
import FormLabel from '@mui/material/FormLabel';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import TextareaAutosize from '@mui/base/TextareaAutosize';
import Autocomplete from '@mui/material/Autocomplete';

const NewProcess = (props) => {

  const [record, setRecord] = React.useState({});

  const [message, setMessage] = React.useState("");

  const statusCodes = [
    { title: "DRAFT", code: "DRAFT" },
    { title: 'Published', code: "PUBLISHED" },
    { title: 'Deactivated', code: "DEACTIVATED" }
  ]; 

  const defaultStatusProps = {
    options: statusCodes,
    getOptionLabel: (option) => option.title,
    isOptionEqualToValue: (option, value) => (option.code == value.code),
  };

  useEffect(() => {
    //console.log('New Process page');
    //console.log(props.pageDataObject);
    if(props.pageDataObject != null){
      setRecord(props.pageDataObject);
    }else{
      setRecord({id:'',code:'',version:'', description:'', definition:'', status:'DRAFT'});
    }
  },[props.pageDataObject]);

  function handleSubmit(){
      //console.log('Submitted : '+ code+' , '+version);
      setMessage("");
      if( record.id == ''){
        
        const url = 'http://localhost:8080/process/def/'+ record.code + '/' + record.version;
        console.log('New record to be created: '+url);
        axios.post( url , record.definition)
          .then(function (response) {
            //console.log(response.data.length);
            console.log(response);
            if(response.status == 200){    
              record.id = response.data.id;
              setMessage("Record created successfully"); 
            }else{
              setMessage("Service error, Please contact IT Admin. Error Message: "+response.statusText); 
            }    
          })
          .catch(function (error) {
            console.log(error);
            setMessage("Service unavailable, Please contact IT Admin. Error Message: "+error.message); 
          });

      }else{
          console.log('Record being updated: '+ record.id);
          //const payload = {'id': id, 'code': code, 'version': version, 'description': description, 'definition': definition};
          const url = 'http://localhost:8080/process/def/'+ record.id;
          axios.put( url , record)
          .then(function (response) {
            //console.log(response.data.length);
            console.log(response);
            if(response.status == 200){
              setMessage("Record updated successfully"); 
            }else {
              setMessage("Service Error, Please contact IT Admin. Error Message: "+response.statusText); 
            }    
          })
          .catch(function (error) {
            console.log(error);
            setMessage("Service unavailable, Please contact IT Admin. Error Message: "+error.message); 
          });
      }
  }

  const handleChange = e =>{
    //  console.log(e.target);
    record[e.target.id] = e.target.value;
    setRecord({ ...record });
  };

  return (

    <div id="layoutDrawer_content">
        
        <main>
            <PageHeader heading="Process Definition" subheading="Add/ Update a Process Definition"/>

            <div className="card card-raised">          

            <React.Fragment>
                
                <Grid container>

                  {
                    message != ''?                        
                        <Grid item xs={12} sm={12} padding={2}>      
                          <Divider textAlign="center">
                            <Chip label={message} />
                          </Divider>
                        </Grid>
                    :
                        <></>
                  }

                  <Grid item xs={12} sm={12} padding={2}>
                    <FormLabel id='lblId'>Id: {record.id}</FormLabel>  
                  </Grid>  
                  <Grid item xs={12} sm={6} padding={2}>                    
                    <TextField required id="code" name="code" label="Code" fullWidth variant="standard"
                      value = {record.code} onChange={handleChange}
                    />
                  </Grid>
                  <Grid item xs={12} sm={3} padding={2}>
                    <TextField required id="version" name="version" label="Version" fullWidth variant="standard"
                      value = {record.version} onChange={handleChange}
                    />                    
                  </Grid>

                  <Grid item xs={12} sm={3} padding={2}>  
                    <Autocomplete
                      {...defaultStatusProps}
                      renderInput={(params) => (
                        <TextField {...params} label="Status" variant="standard"/>
                      )}
                      onChange={handleChange}
                    />            
                  </Grid>

                  <Grid item xs={12} sm={12} padding={2}>
                    <TextField required id="description" name="description" label="Description" fullWidth variant="standard"
                      value = {record.description} 
                      onChange={handleChange}
                    /> 
                  </Grid>
                  <Grid item xs={12} sm={12} padding={2}>
                    <TextareaAutosize id="definition" name="definition" label="Definition" minRows={4} style={{ width: 600 }}
                      value = {record.definition}
                      onChange={handleChange}
                    />
                  </Grid>
                  <Grid item xs={12} sm={6} padding={2}>
                      <div style={{float: 'right'}}>    
                        <Button variant="contained" size="medium" endIcon={<AddIcon />} onClick={() => handleSubmit() }>Save</Button>
                      </div>  
                  </Grid> 
                  <Grid item xs={12} sm={6} padding={2}>
                        <Button variant="outlined" size="medium" endIcon={<DeleteIcon />} color="success" onClick={() => handleSubmit() }>Clear</Button>
                  </Grid>        

                </Grid>
              </React.Fragment>

              </div>

        </main>

        <Footer/>  
    </div>
    
    );
  };
  
export default NewProcess;