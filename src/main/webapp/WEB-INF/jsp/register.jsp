<%@ page pageEncoding="UTF-8" %>
<%@ include file="template/debut.jsp"%>
<div class="container login-container">
    <div class="row justify-content-md-center">
        <div class="col-md-6 login-form">
            <h3>Remplissez tous les champs pour vous enregistrer</h3>
            <%@ include file="template/alerts.jsp"%>
            <form action="Register" method="post">
                <div class="form-group row mt-2">
                    <label>
                        <input name="login" type="text" class="form-control"
                               placeholder="Login" value=""/>
                    </label>
                </div>
                <div class="form-group row mt-2">
                    <label>
                        <input name="password" type="password" class="form-control"
                               placeholder="Mot de passe" value=""/>
                    </label>
                </div>
                <div class="form-group row mt-2">
                    <label>
                        <input name="nom" type="text" class="form-control"
                               placeholder="Nom" value=""/>
                    </label>
                </div>
                <div class="form-group mt-2 text-center">
                    <input type="submit" class="btnSubmit" value="Enregistrer"/>
                </div>
                <div class="form-group mt-5 text-center">
                    <a href="<c:url value="Login"/>" class="ForgetPwd">Déjà enregistré ?</a>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="template/fin.jsp"%>