import React, {useState, useEffect} from 'react';
import axios from 'axios';
import styles from '../css/main.css';
import {Container,Box,AppBar,Toolbar,Typography,Button,IconButton,Drawer,List,
Divider,ListItem,ListItemText, ListItemButton,ListItemIcon}from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import HomeIcon from '@mui/icons-material/Home';
import LoginIcon from '@mui/icons-material/Login';
import LogoutIcon from '@mui/icons-material/Logout';
import HowToRegIcon from '@mui/icons-material/HowToReg';
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import ChatIcon from '@mui/icons-material/Chat';
import LocalHospitalIcon from '@mui/icons-material/LocalHospital';
import AccountBalanceIcon from '@mui/icons-material/AccountBalance';
import MilitaryTechIcon from '@mui/icons-material/MilitaryTech';
import DensityMediumIcon from '@mui/icons-material/DensityMedium';
import DescriptionIcon from '@mui/icons-material/Description';
import VolunteerActivismIcon from '@mui/icons-material/VolunteerActivism';

export default function Header(props) {

    console.log(sessionStorage)


    if(sessionStorage.length<=0){
        console.log(sessionStorage)
        console.log('세션스토리지 비어있음')
        sessionStorage.setItem('email', null);
        sessionStorage.setItem('loginType', null);
    }

    const logOut = () => {
        sessionStorage.setItem('email', null);
        sessionStorage.setItem('loginType', null);
        axios.get("/member/logout");
        window.location.href = '/';
    };

    // Drawer
    const [state, setState] = React.useState({ left: false });
    const toggleDrawer = (anchor, open) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
        return;
    }
    setState({ ...state, [anchor]: open });
    };

    const list = (anchor) => (
        < Box
            sx={{ width: anchor === 'top' || anchor === 'bottom' ? 'auto' : 250 }}
            role="presentation"
            onClick={toggleDrawer(anchor, false)}
            onKeyDown={toggleDrawer(anchor, false)}
        >
        <List>
            <ListItem key='HOME' disablePadding>
                <ListItemButton href='/'>
                    <ListItemIcon>
                    <HomeIcon />
                    </ListItemIcon>
                    <ListItemText primary='HOME' />
                </ListItemButton>
              </ListItem>
          </List>
          <Divider />
          <List>
            {[{"name":'CHAT',"link":'/chattinglist'},{"name":'HOSPITAL',"link":'/hospital/hospitallist'},
            {"name":'GOVERMENT',"link":'/government/info'},{"name":'CHALLENGE',"link":'/challenge/challenge'},
            {"name":'HEART',"link":'/simritest/info'},
            {"name":'LIST',"link":'/board/boardlist'}].map((text, index) => (
              <ListItem key={text} disablePadding>
                <ListItemButton href={ text.link }>
                  <ListItemIcon>
                    {index == 0 ? <ChatIcon />           :
                     index == 1 ? <LocalHospitalIcon />  :
                     index == 2 ? <AccountBalanceIcon /> :
                     index == 3 ? <MilitaryTechIcon />   :
                     index == 4 ? <VolunteerActivismIcon />
                     : <DescriptionIcon />}
                  </ListItemIcon>
                  <ListItemText primary={text.name} />
                </ListItemButton>
              </ListItem>
            ))}
          </List>
          { sessionStorage.getItem('email') == 'null' ? (
              <>
              <Divider />
                <List>
                  {[{"name":'LOGIN',"link":'/login'},{"name":'SIGNUP',"link":'/signup'},
                    {"name":'HLOGIN',"link":'/hlogin'},{"name":'HSIGNUP',"link":'/hsignup'},
                    {"name":'ADMIN',"link":'/admin/dashboard'}].map((text, index) => (
                    <ListItem key={text} disablePadding>
                      <ListItemButton href={ text.link }>
                        <ListItemIcon>
                          { index == 0 ? <LoginIcon />      :
                            index == 1 ? <HowToRegIcon />   :
                            index == 2 ? <LoginIcon />      :
                            index == 3 ? <HowToRegIcon />
                          : <AdminPanelSettingsIcon />}
                        </ListItemIcon>
                        <ListItemText primary={text.name} />
                      </ListItemButton>
                    </ListItem>
                  ))}
                </List>
              </>
            ) : (
              <>
                <Divider />
                <List>
                  {[{"name":'LOGOUT',"link":'/'},{"name":'ADMIN',"link":'/admin/dashboard'}].map((text, index) => (
                    <ListItem key={text} disablePadding>
                    { index == 0 ?
                        (<>
                          <ListItemButton onClick={logOut} >
                            <ListItemIcon>
                               <LogoutIcon />
                            </ListItemIcon>
                            <ListItemText primary={text.name} />
                          </ListItemButton>
                        </>)
                      : (<>
                          <ListItemButton href={ text.link } >
                             <ListItemIcon>
                                <AdminPanelSettingsIcon />
                             </ListItemIcon>
                             <ListItemText primary={text.name} />
                          </ListItemButton>
                        </>)
                      }
                    </ListItem>
                  ))}
                </List>
              </>
            )}
        </Box>
    );

    return (
    <>
        <Box sx={{ flexGrow: 1 }}>
          <AppBar position="static" style={{ backgroundColor: "#DCBE70" }}>
            <Toolbar>
              <React.Fragment key='left' >
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                onClick={toggleDrawer('left', true)}
                sx={{ mr: 2 }}
              >
                <MenuIcon />
              </IconButton>
                <Drawer
                  anchor='left'
                  open={state['left']}
                  onClose={toggleDrawer('left', false)}
                >
                  {list('left')}
                </Drawer>
                </React.Fragment>
              <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                스르륵
              </Typography>
            </Toolbar>
          </AppBar>
        </Box>
    </>
    );
}