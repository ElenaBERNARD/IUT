import {getRequest, postRequest, patchRequest} from "./axios.service";
import store from '../store'

function authHeader() {
  // get the token in the browser local storage
  // it could be from the vuex store, but in this case, we must import it.
  let userdata = JSON.parse(localStorage.getItem('userdata'));

  if (userdata && userdata.xsrfToken) {
    // for Node.js Express back-end
    return {'x-xsrf-token': userdata.xsrfToken};
  } else {
    return {};
  }
}

async function refreshService(service, data, name, typeReq) {
  let response = null;
  let refreshed = 0;
  [refreshed, response] = await doRefresh();
  // if refresh success => make the request again
  if (refreshed) {

    if (typeReq === "get") {
      return getRequest(service, name);
    }
    else if (typeReq === "post") {
      return postRequest( service, data, name);
    }
    else if (typeReq === "patch") {
      return patchRequest(service, data, name);
    }
  }
  // if refresh service fails, return the response object that contains the error
  return response
}

async function doRefresh() {
  //let typeReq = 'patch'
  let service = '/auth/refresh'
  let serviceName = "DOREFRESH";
  console.log('SERVICE: '+serviceName);

  let userdata = JSON.parse(localStorage.getItem('userdata'));
  // refresh token exists so try a refresh
  if (userdata && userdata.refreshToken) {
    console.log("using refresh service with token: "+userdata.refreshToken)
    let response = await patchRequest( service, {refreshToken: userdata.refreshToken}, 'DOREFRESH');
    if (response.error === 0) {
      // refresh the token in the store,
      // NB: response.data is from axios, and it contains an object with a field refreshToken containing the new string for the refresh token
      store.commit('auth/refresh', response.data, {root: true});
      return [ true, response];
    }
    else {
      return [false, response]
    }
  }
}

async function loginService(user) {
  //let typeReq = 'post'
  let service = '/auth/signin'
  let serviceName = "LOGIN";
  console.log('SERVICE: '+serviceName);
  return postRequest( service, user);
}

export default {
  authHeader,
  loginService,
  refreshService,
}