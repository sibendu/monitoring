import React , { useState  } from 'react';
import { DataGrid, useGridApiRef, GridToolbar , GridRowModes, GridSearchIcon} from '@mui/x-data-grid';

import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';

import Badge from '@mui/material/Badge';
import MailIcon from '@mui/icons-material/Mail';

import DeleteIcon from '@mui/icons-material/Delete';
import SendIcon from '@mui/icons-material/Send';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import FavoriteIcon from '@mui/icons-material/Favorite';
import NavigationIcon from '@mui/icons-material/Navigation';
import Switch from '@mui/material/Switch';
import Divider from '@mui/material/Divider';
import Chip from '@mui/material/Chip';

import FormLabel from '@mui/material/FormLabel';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Checkbox from '@mui/material/Checkbox';
import Autocomplete from '@mui/material/Autocomplete';

import Footer from "../../common/Footer";

import PageHeader from '../../common/PageHeader';

const SearchTask = () => {

  const [searchingInd, setSearchingInd] = React.useState(false);
  const [searchedInd, setSearchedInd] = React.useState(false);
  const [pageSize, setPageSize] = React.useState(10);
  const [recordSelected, setRecordSelected] = useState({});

  const [result, setResult] = React.useState({
      "records":[
        { id: 11, lastName: 'Snow', firstName: 'Jon', age: 35 },
        { id: 12, lastName: 'Lannister', firstName: 'Cersei', age: 42 },
        { id: 13, lastName: 'Lannister', firstName: 'Jaime', age: 45 },
        { id: 14, lastName: 'Stark', firstName: 'Arya', age: 16 },
        { id: 15, lastName: 'Targaryen', firstName: 'Daenerys', age: null },
        { id: 16, lastName: 'Melisandre', firstName: null, age: 150 },
        { id: 17, lastName: 'Clifford', firstName: 'Ferrara', age: 44 },
        { id: 18, lastName: 'Frances', firstName: 'Rossini', age: 36 },
        { id: 19, lastName: 'Roxie', firstName: 'Harvey', age: 65 },
        { id: 20, lastName: 'John', firstName: 'Ferrara', age: 44 },
        { id: 21, lastName: 'Franc', firstName: 'Rossini', age: 36 },
        { id: 22, lastName: 'Rox', firstName: 'Harvey', age: 65 }
      ]
  });

  const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'firstName',headerName: 'First name',width: 150, editable: false,headerClassName: 'super-app-theme--header',headerAlign: 'center'},
    { field: 'lastName', headerName: 'Last name', width: 150, editable: false, headerClassName: 'super-app-theme--header', headerAlign: 'center'},
    { field: 'age', headerName: 'Age', type: 'number', width: 110, editable: false, headerClassName: 'super-app-theme--header', headerAlign: 'center'},
    { field: 'fullName', headerName: 'Full name', description: 'This column has a value getter and is not sortable.', sortable: false, width: 160,
      valueGetter: (params) =>
        `${params.row.firstName || ''} ${params.row.lastName || ''}`,
      headerClassName: 'super-app-theme--header',
      headerAlign: 'center',  
    },
    { field: "actionEdit", headerName: "Edit", sortable: false, headerClassName: 'super-app-theme--header', headerAlign: 'center',
      renderCell: (params) => {
        const onClick = (e) => {
          e.stopPropagation(); // don't select this row after clicking 
          alert("Editing record: "+params.id);
        };
        return (
          <button className="btn btn-lg btn-text-black btn-icon" type="button" onClick={onClick}><i className="material-icons">edit</i></button>
        );
      }
    },
    { field: "actionDelete", headerName: "Remove", sortable: false, headerClassName: 'super-app-theme--header', headerAlign: 'center',
      renderCell: (params) => {
        const onClick = (e) => {
          e.stopPropagation(); // don't select this row after clicking  
          alert("Deleting record: "+params.id);
        };
        return (
          <button className="btn btn-lg btn-text-black btn-icon" type="button" onClick={onClick}><i className="material-icons">delete</i></button>
        );
      }
    },
  ];

  function search(){
    const searchResponse = {
      totalMatch : 1000,
      returnCount: 2,
      "message": "1000 matching records found",
      "records":[
        { id: 11, lastName: 'Snow', firstName: 'Jon', age: 35 },
        { id: 15, lastName: 'Targaryen', firstName: 'Daenerys', age: null }
      ]
    };

    setSearchedInd(true);
    setResult(searchResponse);
  }

  function addNewRecord(){
    alert(result.records);
  }

  function deleteRecords(){
    alert("Delete records");
  }

  return (

    <div id="layoutDrawer_content">
        
        <main>
            
            <div className="container-xl p-5">
                
            <PageHeader heading="Search All Tasks" subheading=""/>
            
            <div className="card card-raised">

            <React.Fragment>
                
                <Grid container>

                  <Grid item xs={12} sm={12} padding={2}>
                    <div className="row align-items-right mb-1" style={{float: 'right'}}>
                      <div className="col flex-shrink-0 mb-1 mb-md-0">
                        <Button variant="contained" size="small" endIcon={<GridSearchIcon/>} onClick={() => setSearchingInd(!searchingInd)}>Search</Button>
                        &nbsp;&nbsp;&nbsp;
                        <Button variant="contained" size="small" endIcon={<AddIcon/>} onClick={() => addNewRecord()}>Add New</Button>
                        &nbsp;&nbsp;&nbsp;
                        <Button variant="contained" size="small" endIcon={<DeleteIcon/>} onClick={() => deleteRecords()}  disabled={recordSelected.length > 0 ? false: true}>Delete</Button>
                      </div>
                    </div>    
                  </Grid>

                  {
                    searchingInd?
                      <>   
                        <Grid item xs={12} sm={4} padding={2}>
                          <TextField required id="firstName" name="firstName" label="First name" fullWidth autoComplete="first-name" variant="standard"/>
                        </Grid>
                        <Grid item xs={12} sm={4} padding={2}>
                          <TextField required id="lastName" name="lastName" label="Last name" fullWidth autoComplete="given-name" variant="standard"/>
                        </Grid>
                        <Grid item xs={12} sm={4} padding={2}>
                          <TextField required id="age" name="age" label="Age" fullWidth autoComplete="age" variant="standard"/>
                        </Grid>
                        <Grid item xs={12} sm={12} padding={2}>      
                          <Button variant="contained" size="small" endIcon={<GridSearchIcon/>} onClick={() => search()}>Search</Button>
                        </Grid>
                      </>    
                    :
                      <></>
                  }

                  {
                    searchedInd?                        
                        <Grid item xs={12} sm={12} padding={2}>      
                          <Divider textAlign="center">
                            <Chip label={result.records.length > 0? result.message: "No matching records"} />
                          </Divider>
                        </Grid>
                    :
                        <></>
                  }

                  <Grid item xs={12} sm={12} padding={2}>
                    <DataGrid id="taskList"
                      rows={result.records}
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

            </div>
        </main>

        <Footer/>  
    </div>
    
    );
  };
  
export default SearchTask;