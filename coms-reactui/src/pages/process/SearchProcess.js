import React , { useEffect, useState  } from 'react';

import Footer from "../../common/Footer";
import PageHeader from '../../common/PageHeader';

import axios from "axios";

import { DataGrid, GridToolbar , GridSearchIcon} from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import Divider from '@mui/material/Divider';
import Chip from '@mui/material/Chip';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';

const SearchProcess = (props) => {
  
  const [searchingInd, setSearchingInd] = React.useState(false);
  const [searchedInd, setSearchedInd] = React.useState(false);
  const [pageSize, setPageSize] = React.useState(10);
  const [recordSelected, setRecordSelected] = useState({});

  const [searchCode, setSearchCode] = React.useState("");
  const [searchVersion, setSearchVersion] = React.useState("");

  const [records, setRecords] = React.useState([]);
  const [message, setMessage] = useState("");

  const errorMessage = "There is an error, please contact IT Admin. Error Message: ";
  const successMessage = "Processed successfully";

  function fetchData() {
    //console.log('Fetching data');
    const url = 'http://localhost:8080/process/def/';    
    axios.get(url).
      then(response => {
        //console.log(response.data);
        if(response.status == 200){
          setRecords(response.data);        
        }else{
          setMessage(errorMessage + response.statusText); 
        } 
      }).catch(error => {
        console.log('Error');
        setMessage(errorMessage + error.message);
      });      
  }

  useEffect(() => {
    fetchData()
  },[])


  function handleDelete(record){
    var payload = [];
    if( record != null){
      payload.push(record.id);
    }else{
      payload = recordSelected;
    } 
    
    const url = 'http://localhost:8080/process/def/';
      
    axios.post(url , payload)
      .then(function (response) {
        if(response.status == 200){
          setMessage(successMessage); 
          fetchData();
        }else{
          setMessage(errorMessage + response.statusText); 
        }   
      })
      .catch(error => {
        console.log(error);
        setMessage(errorMessage + error.message); 
      });
  
  }

  const columns = [
    { field: 'id', headerName: 'ID', width: 30 },
    { field: 'code',headerName: 'Code',width: 200, editable: false,headerClassName: 'super-app-theme--header',headerAlign: 'center'},
    { field: 'version', headerName: 'Version', width: 80, editable: false, headerClassName: 'super-app-theme--header', headerAlign: 'center'},
    { field: 'status', headerName: 'Status',  width: 80,  editable: false, headerClassName: 'super-app-theme--header', headerAlign: 'center'},
    { field: 'description', headerName: 'Description', flex: 1, editable: false, headerClassName: 'super-app-theme--header', headerAlign: 'center'},
    { field: "actionEdit", headerName: "Edit", width: 30, sortable: false, headerClassName: 'super-app-theme--header', headerAlign: 'center',
      renderCell: (params) => {
        const onClick = (e) => {
          e.stopPropagation(); // don't select this row after clicking 
          props.onClick("NewProcess", params.row);
        };
        return (
          <button className="btn btn-lg btn-text-black btn-icon" type="button" onClick={onClick}><i className="material-icons">edit</i></button>
        );
      }
    },
    { field: "actionDelete", headerName: "Remove", width: 30, sortable: false, headerClassName: 'super-app-theme--header', headerAlign: 'center',
      renderCell: (params) => {
        const onClick = (e) => {
          e.stopPropagation(); // don't select this row after clicking  
          handleDelete(params.row);
        };
        return (
          <button className="btn btn-lg btn-text-black btn-icon" type="button" onClick={onClick}><i className="material-icons">delete</i></button>
        );
      }
    },
  ];

  function search(){
    //console.log('Searching : '+ searchCode+' , '+searchVersion);
    setMessage("");
    axios.post('http://localhost:8080/process/def/search', {
      code: searchCode,
      version: searchVersion
    })
    .then(function (response) {
      //console.log(response.data.length);
      setRecords(response.data);
      setMessage("Matching records: "+response.data.length);
    })
    .catch(function (error) {
      console.log(error);
      setMessage(error.message);
    });
    setSearchedInd(true);  
  }

  return (

    <div id="layoutDrawer_content">
        
        <main>
            <PageHeader heading="Process Definitions" subheading="List/ Search Process Definitions"/>
            
            <div className="card card-raised">

            <React.Fragment>
                
                <Grid container>

                  <Grid item xs={12} sm={12} padding={2}>
                    <div className="row align-items-right mb-1" style={{float: 'right'}}>
                      <div className="col flex-shrink-0 mb-1 mb-md-0">
                        <Button variant="contained" size="small" endIcon={<GridSearchIcon/>} onClick={() => setSearchingInd(!searchingInd)}>Search</Button>
                        &nbsp;&nbsp;&nbsp;
                        <Button variant="contained" size="small" endIcon={<AddIcon/>} onClick={() => props.onClick("NewProcess", null)}>Add New</Button>
                        &nbsp;&nbsp;&nbsp;
                        <Button variant="contained" size="small" endIcon={<DeleteIcon/>} onClick={() => handleDelete()}  disabled={recordSelected.length > 0 ? false: true}>Delete</Button>
                      </div>
                    </div>    
                  </Grid>

                  {
                    searchingInd?
                      <>   
                        <Grid item xs={12} sm={4} padding={2}>
                          <TextField required id="code" name="code" label="Code" fullWidth variant="standard" value={searchCode} onChange={(e) => setSearchCode(e.target.value)}/>
                        </Grid>
                        <Grid item xs={12} sm={4} padding={2}>
                          <TextField id="version" name="version" label="Version" fullWidth variant="standard" value={searchVersion} onChange={(e) => setSearchVersion(e.target.value)}/>
                        </Grid>
                        <Grid item xs={12} sm={12} padding={2}>      
                          <Button variant="contained" size="small" endIcon={<GridSearchIcon/>} onClick={() => search()}>Search</Button>
                        </Grid>
                      </>    
                    :
                      <></>
                  }

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

                  {
                    searchedInd?                        
                        <Grid item xs={12} sm={12} padding={2}>      
                          <Divider textAlign="center">
                            <Chip label={message} />
                          </Divider>
                        </Grid>
                    :
                        <></>
                  }

                  <Grid item xs={12} sm={12} padding={2}>
                    <DataGrid id="taskList"
                      rows={records}
                      columns={columns}
                      pageSize={pageSize}
                      onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
                      rowsPerPageOptions={[5, 10, 20]}
                      checkboxSelection
                      disableSelectionOnClick
                      autoHeight={true}
                      autoWidth={true}
                      rowHeight={25}
                      components={{ Toolbar: GridToolbar }} 
                      onSelectionModelChange={(ids) => setRecordSelected(ids)}
                      density='comfortable'
                      sx={{
                        boxShadow: 3,
                        border: 0,
                        borderColor: 'primary.light',
                        '& .MuiDataGrid-row:hover': {
                          color: 'blue',
                          background: '#E0E0E0'
                        },
                      }}
                    />
                  </Grid>

                </Grid>
              </React.Fragment>

              </div>

        </main>

        <Footer/>  
    </div>
    
    );
  };
  
export default SearchProcess;