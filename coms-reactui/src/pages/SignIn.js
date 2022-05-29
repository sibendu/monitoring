import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';

import Footer from '../common/Footer';

import { GoogleLogin } from 'react-google-login';

const clientId = '100566415934-k4r4eks1aj94vcadle7kca0uqkv5etk4.apps.googleusercontent.com';

const theme = createTheme();

async function loginUser(credentials) {
    console.log('loginUser');
    /* 
    return fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'	
      },
      body: JSON.stringify(credentials)
    })
      .then(data => data.json())
    */
    if(credentials.username === 'super'){
        //alert('super'); 
        return {'name':'super', 'roles':['SUPERADMIN']}; 
    }else if(credentials.username === 'admin'){
        //alert('admin'); 
        return {'name':'admin', 'roles':['ADMIN']}; 
    }else if(credentials.username === 'po'){
        //alert('po');
        return {'name':'po', 'roles':['PROCESS_OWNER']};
    }
    return {'name':'emp', 'roles':['EMPLOYEE']};
}

export default function SignIn({ setUser }) {

  const handleSubmit = async event => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const user = await loginUser({
        username: data.get('email'),
        password: data.get('password')
    });
    setUser(user);
  }

  const onSuccess = (res) => {
    console.log('[Login success] currentUser:', res.profileObj);
    const user = {'name':res.profileObj.givenName, 'roles':['SUPERADMIN'], 'fullname': res.profileObj.name, 'email': res.profileObj.email, 'imageUrl': res.profileObj.imageUrl, 'googleId': res.profileObj.googleId}; 
    setUser(user);
  }

  const onFailure = (res) => {
    console.log('[Login failure] res:', res);
  }

  return (

    <div id="layoutDrawer_content">    
        <main>
        
<ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
            <LockOutlinedIcon />
          </Avatar>

          <Typography component="h1" variant="h5">
            Sign in using
          </Typography>

          <GoogleLogin
              clientId={clientId}
            buttonText="Login"
            onSuccess={onSuccess}
            onFailure={onFailure}
            cookiePolicy={'single_host_origin'}
            style={{marginTop: '100px'}}
            isSignedIn={true}
          />

          <Typography component="h1" variant="h5">
            Or, 
          </Typography>

          <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            <Grid container>
              <Grid item xs>
                <Link href="#" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link href="#" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Footer sx={{ mt: 8, mb: 4 }} />
      </Container>
    </ThemeProvider>



     	</main>
    </div>



    
  );
}