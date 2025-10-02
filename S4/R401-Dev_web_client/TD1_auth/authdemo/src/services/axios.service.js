import axios from 'axios'
import AuthService from './auth.service'
import Config from '../commons/config'
import {ERR_AUTH_NO_TOKEN, ERR_AUTH_TOKEN_EXPIRED, ERR_AUTH_NOT_AUTHORIZED, ERR_UNDEFINED, ERR_NETWORK} from "../commons/errors"
import store from '../store'
import router from "../router";

// create a special axiosAgent agent that works with the apidemo API
const axiosAgent = axios.create({
    baseURL: Config.urlAPI,
    withCredentials : true,
});

// add x-xsrf-token to all requests to the API
axiosAgent.interceptors.request.use(
    config => {
        return {
            ...config,
            headers: {
                ...AuthService.authHeader()
            },
        };
    },
    error => Promise.reject(error)
);

async function handleError(service, data, name, typeReq, err) {
    if (err.response) {
        // la requête a été reçue par le serveur mais celui-ci renvoie un status != 2XX, ce qui signifie
        // une erreur. Par exemple, il peut renovyer un status 404 pour dire que la ressource demandée n'existe pas.
        console.log("ERROR while calling SERVICE " + name + ": " + JSON.stringify(err.response));
        // now check if the jwt token must be refreshed because cookie containing jwt expired
        if ((err.response.data.error === ERR_AUTH_NO_TOKEN) ||
            (err.response.data.error === ERR_AUTH_NOT_AUTHORIZED) ||
            (err.response.data.error === ERR_AUTH_TOKEN_EXPIRED) ) {
            store.commit('auth/logout')
            store.commit('errors/pushError', 'problems with tokens (none | expired | differs) => logout')
            router.push('/')
        }
        return {
            data: {
                error: err.response.data.error,
                data: err.response.data.data
            }
        } // pass the whole object from server because it contains a data field too.

    } else if (err.request) {
        // la requete a été envoyée mais aucune réponse reçue.
        console.log("NETWORK ERROR while calling SERVICE " + name + ": " + JSON.stringify(err.request));
        return {
            data: {
                error: ERR_NETWORK,
                data: 'Le serveur est injoignable ou l\'URL demandée n\'existe pas'
            }
        };
    } else {
        // tout autre cas
        console.log("UNKNOWN ERROR while calling SERVICE " + name);
        return {
            data: {
                error: ERR_UNDEFINED,
                data: 'Erreur inconnue'
            }
        };
    }
}


async function getRequest(service, name) {
    console.log("GET route "+service)
    let response = null
    try {
        response = await axiosAgent.get(service)
    } catch (err) {
        // le catch se fait si le serveur répond avec une erreur type 4XX, 5XX, ou bien si le serveur est off
        // dans ce cas, on appelle la méthode pour traiter ces types d'erreurs
        response = await handleError(service, {}, name, 'get', err);
    }
    return response.data;
}

// NB: pour une requête post/patch, les données associées à la requête sont transmises
// par axios sous la forme d'un objet JSON contenant ces données, et axios les transmet
// de façon compactée dans le "corps" de la requête (c.a.d. la partie body). Du côté serveur, il faut "analyser"
// le corps (donc utiliser le module body-parser) afin d'avoir le contenu de l'objet dans req.body.
// Dans la méthode ci-dessous, le paramètre data correspond à l'objet JSON
async function postRequest(service, data, name) {
    console.log("POST route "+service)
    let response = null
    try {
        response = await axiosAgent.post(service, data)
    } catch (err) {
        // le catch se fait si le serveur répond avec une erreur type 4XX, 5XX, ou bien si le serveur est off
        // dans ce cas, on appelle la méthode pour traiter ces types d'erreurs
        response = await handleError(service, data, name, 'post', err);
    }
    return response.data;
}

async function patchRequest(service, data, name) {
    console.log("PATCH route "+service)
    let response = null
    try {
        response = await axiosAgent.patch(service, data)
    } catch (err) {
        // le catch se fait si le serveur répond avec une erreur type 4XX, 5XX, ou bien si le serveur est off
        // dans ce cas, on appelle la méthode pour traiter ces types d'erreurs
        response = await handleError(service, data, name, 'patch', err);
    }
    return response.data;
}

export {
    getRequest,
    postRequest,
    patchRequest,
}
