import { getRequest, postRequest } from '../services/axios.service.js'

async function loginUserFromAPI(info) {
  const login = info.login
  const password = info.password
  console.log('Login : ' + login)
  console.log('Pass : ' + password)
  return postRequest('/auth/signin', { login, password }, 'signin', 'auth')
}
async function getUserInfoFromAPI(login) {
  return getRequest('/user/getuser/' + login, 'getUserInfo', 'auth', true)
}

async function registerUserFromAPI(userInfo) {
  return postRequest('/user/register', userInfo, 'signup', 'auth')
}

async function loginUserService(info) {
  let answer = await loginUserFromAPI(info)
  return answer
}

async function getUserInfoService(login) {
  let answer = await getUserInfoFromAPI(login)
  return answer
}

async function registerUserService(userInfo) {
  let answer = await registerUserFromAPI(userInfo)
  return answer
}

export { loginUserService, getUserInfoService, registerUserService }
