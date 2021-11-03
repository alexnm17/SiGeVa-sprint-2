import React, { Component } from "react";
import { Breadcrumb, Button } from "react-bootstrap"
import axios from "axios";
 

class Login {
		constructor() {
			self.dni = ko.observable("71360720T");
			self.password = ko.observable("12345678");
			self.message = ko.observable();
			self.error = ko.observable();
			
			self.headerConfig = ko.observable({
				'view' : [],
				'viewModel' : null
			}
		}

		login() {
			var self = this;
			var info = {
				email : this.dni(),
				pwd : this.password()
			};
			var data = {
				data : JSON.stringify(info),
				url : "user/login",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					
				},
				error : function(response) {
					
				}
			};
			$.ajax(data);
		}
		
	
	}

	return Login;
;
