import React , { useEffect, useRef, useContext  } from 'react';
import axios from 'axios';
import {Box,AppBar,Toolbar,Typography,IconButton,Drawer,List,
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
import DescriptionIcon from '@mui/icons-material/Description';
import VolunteerActivismIcon from '@mui/icons-material/VolunteerActivism';
import NightsStayIcon from '@mui/icons-material/NightsStay';
import { WebSocketContext } from './chatting/WebSocketContext';

export default function Header(props) {
    let websocket = useContext(WebSocketContext);

    console.log(sessionStorage)

    const email = sessionStorage.getItem('email');
    const loginType = sessionStorage.getItem('loginType');
    /*
    useEffect( ()=>{
        if(email!==null&&email!=="null"){
            if(websocket===null){
                websocket = new WebSocket("ws://localhost:8080/intoHomePage/"+email);
                console.log(websocket);
                console.log(JSON.stringify(websocket));
                websocket.onopen = () => {
                    websocket.send(JSON.stringify({ type : "enter", loginType : loginType }));
                };
                sessionStorage.setItem('websocket', JSON.stringify(websocket));
            }
        }
    } , [])
    */

    if(sessionStorage.length<=0){
        sessionStorage.setItem('email', null);
        sessionStorage.setItem('loginType', null);
        sessionStorage.setItem('websocket',null)
    }
    // 로그인
    useEffect( ()=>{

        axios.get("/member/info").then( r => {console.log(r);
            if( r.data !== ''){ // 로그인되어 있으면 // 서비스에서 null 이면 js에서 ''이다.
                // js 로컬 스토리지에 저장
                if(r.data.split(' ')[0] === 'DOCTOR'){
                    sessionStorage.setItem("email" , r.data.split(' ')[1] );
                    sessionStorage.setItem('loginType', "doctor");
                }else{
                    sessionStorage.setItem("email" , r.data );
                    sessionStorage.setItem('loginType', "normal");
                }
            }
        })
    }, [])

    // 로그아웃
    const logOut = () => {
        sessionStorage.setItem('email', null);
        sessionStorage.setItem('loginType', null);
        sessionStorage.setItem('websocket', null);
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
            {"name":'HEART',"link":'/simritest/info'}].map((text, index) => (
              <ListItem key={text} disablePadding>
                <ListItemButton to={ text.link }>
                  <ListItemIcon>
                    {index === 0 ? <ChatIcon />           :
                     index === 1 ? <LocalHospitalIcon />  :
                     index === 2 ? <AccountBalanceIcon /> :
                     index === 3 ? <MilitaryTechIcon />
                     : <VolunteerActivismIcon />}
                  </ListItemIcon>
                  <ListItemText primary={text.name} />
                </ListItemButton>
              </ListItem>
            ))}
          </List>
          { sessionStorage.getItem('email') === 'null' ? (
              <>
              <Divider />
                <List>
                  {[{"name":'LOGIN',"link":'/member/login'},{"name":'SIGNUP',"link":'/signup'}].map((text, index) => (
                    <ListItem key={text} disablePadding>
                      <ListItemButton href={ text.link }>
                        <ListItemIcon>
                          { index === 0 ? <LoginIcon />      :
                            index === 1 ? <HowToRegIcon />
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
                    <ListItem key='LOGOUT' disablePadding>
                      <ListItemButton onClick={logOut} >
                        <ListItemIcon>
                           <LogoutIcon />
                        </ListItemIcon>
                        <ListItemText primary='LOGOUT' />
                      </ListItemButton>
                    </ListItem>
                </List>
                <Divider />
                <List>
                { loginType === "doctor" ?
                  (<>
                    <ListItem key='LIST' disablePadding>
                      <ListItemButton href='/board/doctor/boardlist'>
                        <ListItemIcon>
                            <DescriptionIcon />
                        </ListItemIcon>
                        <ListItemText primary='LIST' />
                      </ListItemButton>
                     </ListItem>
                    </>)
                  : loginType === "admin" ?
                  (<>
                      <ListItem key='ADMIN' disablePadding>
                        <ListItemButton href='/admin/dashboard'>
                          <ListItemIcon>
                              <AdminPanelSettingsIcon />
                          </ListItemIcon>
                          <ListItemText primary='ADMIN' />
                        </ListItemButton>
                       </ListItem>
                    </>):(<>  </>)
                }
                </List>
              </>
            )}
        </Box>
    );

    return (
    <>
        <Box sx={{ flexGrow: 1 , marginBottom:'20px'}}>
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
              <Typography variant="h6" component="a" sx={{ flexGrow: 1 }} href="/"  >
                <NightsStayIcon />
                스르륵
              </Typography>
            </Toolbar>
          </AppBar>
        </Box>
    </>
    );
}