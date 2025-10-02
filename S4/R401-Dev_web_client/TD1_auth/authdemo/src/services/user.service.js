import {getRequest, postRequest} from "./axios.service";

async function getUser(login) {
  let service = '/user/getuser/'+login
  let serviceName = "GETUSER";
  return await getRequest( service, serviceName);
}

async function getUsers() {
  let service = '/user/getusers'
  let serviceName = "GETUSERS";
  return await getRequest( service, serviceName);
}

async function registerUser(data) {
  let service = '/user/register'
  let serviceName = "REGISTERUSER";
  return await postRequest( service, data, serviceName);
}

export default {
  getUser,
  getUsers,
  registerUser,
}
