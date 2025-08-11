	' Code for IE under XP or earlier. Placed here since script src=... does not seem to work.
    Dim useold
    useold=false
	
				
    Function GetProviderList()
	
	   Dim CspList, cspIndex, ProviderName
	   On Error Resume Next
	
	   count = 0
	   base = -1
	   enhanced = 0
	   CspList = ""
	   ProviderName = ""
	
	   For ProvType = 0 to 13
	      cspIndex = 0
	      newencoder.ProviderType = ProvType
	      ProviderName = newencoder.enumProviders(cspIndex,0)
	
	      while ProviderName <> ""
	         Set oOption = document.createElement("option")
	         oOption.text = ProviderName
	         oOption.value = ProvType
	         Document.CertReqForm.CspProvider.add(oOption)
	         if ProviderName = "Microsoft Base Cryptographic Provider v1.0" Then
	            base = count
	         end if
	         if ProviderName = "Microsoft Enhanced Cryptographic Provider v1.0" Then
	            enhanced = count
	         end if
	         cspIndex = cspIndex +1
	         ProviderName = ""
	         ProviderName = newencoder.enumProviders(cspIndex,0)
	         count = count + 1
	      wend
	   Next
	   If base = -1 Then
	     useold=true
	     Document.CertReqForm.classid.value="clsid:43F8F289-7A20-11D0-8F06-00C04FC295E1"
	     count = 0
	     enhanced = 0
	     basename = ""
	     enhancedname = ""
	     CspList = ""
	     ProviderName = ""
	
	     For ProvType = 0 to 13
	         cspIndex = 0
	         oldencoder.ProviderType = ProvType
	         ProviderName = oldencoder.enumProviders(cspIndex,0)
	
	        while ProviderName <> ""
	           Set oOption = document.createElement("option")
	           oOption.text = ProviderName
	           oOption.value = ProvType
	           Document.CertReqForm.CspProvider.add(oOption)
	           if ProviderName = "Microsoft Base Cryptographic Provider v1.0" Then
	            base = count
	           end if
	           if ProviderName = "Microsoft Enhanced Cryptographic Provider v1.0" Then
	            enhanced = count
	           end if
	           cspIndex = cspIndex +1
	           ProviderName = ""
	           ProviderName = oldencoder.enumProviders(cspIndex,0)
	           count = count + 1
	        wend
	     Next
	   End If 
	   Document.CertReqForm.CspProvider.selectedIndex = base
	   if enhanced then
	      Document.CertReqForm.CspProvider.selectedIndex = enhanced
	   end if
   End Function
 
   Function NewCSR(keyflags)
      NewCSR = ""
      szName = "CN=6AEK347fw8vWE424"
       newencoder.reset  
       newencoder.HashAlgorithm = "MD5"
       err.clear
       On Error Resume Next
       set options = document.all.CspProvider.options
       index = options.selectedIndex
       newencoder.providerName = options(index).text
       tmpProviderType = options(index).value
       newencoder.providerType = tmpProviderType
       if Document.CertReqForm.enchancedeid.checked then      
         newencoder.ContainerName = "\Prime EID IP1 (basic PIN)\E"
       end if
       newencoder.KeySpec = 2
       if tmpProviderType < 2 Then
          newencoder.KeySpec = 1
       end if
       
       keysize = document.CertReqForm.keysize.value
       keymask = keysize * 65536
       
       newencoder.GenKeyFlags = keymask OR keyflags
 
       NewCSR = newencoder.createPKCS10(szName, "")
       if len(NewCSR)<>0 then Exit Function
       if newencoder.providerName = "Microsoft Enhanced Cryptographic Provider v1.0" Then
          if MsgBox("1024-bit key generation failed. Would you like to try 512 instead?", vbOkCancel)=vbOk Then
             newencoder.providerName = "Microsoft Base Cryptographic Provider v1.0"
          else
             Exit Function
          end if
       end if
       newencoder.GenKeyFlags = keyflags
       NewCSR = newencoder.createPKCS10(szName, "")
       if len(NewCSR)<>0 then Exit Function
       newencoder.GenKeyFlags = 0
       NewCSR = newencoder.createPKCS10(szName, "")
    End Function
 
   Function OldCSR(keyflags)
      OldCSR = ""
      szName = "CN=6AEK347fw8vWE424"
       oldencoder.reset
       oldencoder.HashAlgorithm = "MD5"
       err.clear
       On Error Resume Next
       set options = document.all.CspProvider.options
       index = options.selectedIndex
       oldencoder.providerName = options(index).text
       tmpProviderType = options(index).value
       oldencoder.providerType = tmpProviderType
       if Document.CertReqForm.enchancedeid.checked then         
         oldencoder.ContainerName = "\Prime EID IP1 (basic PIN)\E"
       end if
       oldencoder.KeySpec = 2
       if tmpProviderType < 2 Then
          oldencoder.KeySpec = 1
       end if
       
       keysize = document.CertReqForm.keysize.value
       keymask = keysize * 65536
       
       oldencoder.GenKeyFlags = keymask OR keyflags
       OldCSR = oldencoder.createPKCS10(szName, "")
       if len(OldCSR)<>0 then Exit Function
       if oldencoder.providerName = "Microsoft Enhanced Cryptographic Provider v1.0" Then
          if MsgBox("1024-bit key generation failed. Would you like to try 512 instead?", vbOkCancel)=vbOk Then
             oldencoder.providerName = "Microsoft Base Cryptographic Provider v1.0"
          else
             Exit Function
          end if
       end if
       oldencoder.GenKeyFlags = keyflags
       OldCSR = oldencoder.createPKCS10(szName, "")
       if len(OldCSR)<>0 then Exit Function
       oldencoder.GenKeyFlags = 0
       OldCSR = oldencoder.createPKCS10(szName, "")
    End Function
    
   	' Used b apply_exp.jspf
	Function GetCSR(exportflag)
		GetCSR = ""
		' Get provider name and type
		Dim ProviderName, ProviderType
		ProviderName = document.all.CspProvider.options(document.all.CspProvider.options.selectedIndex).text
		ProviderType = document.all.CspProvider.options(document.all.CspProvider.options.selectedIndex).value
		g_objPrivateKey.ProviderName = ProviderName
		g_objPrivateKey.ProviderType = ProviderType
		g_objPrivateKey.Length = document.CertReqForm.keysize.value
		If ProviderType < 2 Then
			g_objPrivateKey.KeySpec = 1	'AT_KEYEXCHANGE
		Else
			g_objPrivateKey.KeySpec = 2	'AT_SIGNATURE
		End If
		g_objPrivateKey.MachineContext = false
		g_objPrivateKey.KeyProtection = 1	' (XCN_NCRYPT_UI_PROTECT_KEY_FLAG = 1)
		g_objPrivateKey.ExportPolicy = exportflag	' (XCN_NCRYPT_ALLOW_EXPORT_FLAG = 1)
	    If Document.CertReqForm.enchancedeid.checked then
	    	If Document.CertReqForm.containername = "" Then
				g_objPrivateKey.ContainerName = "\Prime EID IP1 (basic PIN)\E"
			Else
				g_objPrivateKey.ContainerName = Document.CertReqForm.containername.value
			End If
			g_objPrivateKey.Existing = True
		Else
			g_objPrivateKey.Existing = False
		End if
		' Initialize
		Call g_objRequest.InitializeFromPrivateKey(1, g_objPrivateKey, "")	'X509CertificateEnrollmentContext.ContextUser
		Dim X500DistinguishedName
		Set X500DistinguishedName = g_objClassFactory.CreateObject("X509Enrollment.CX500DistinguishedName")
		Call X500DistinguishedName.Encode("CN=6AEK347fw8vWE424", 0)	'XCN_CERT_NAME_STR_NONE
		g_objRequest.Subject = X500DistinguishedName
		' Set hash algo
		Dim CspInformation, CspAlgorithms, CspAlgorithm, nBestIndex, nAlgIndex
		Set CspInformation = g_objCSPInformations.ItemByName(ProviderName)
		Set CspAlgorithms = CspInformation.CspAlgorithms
		nBestIndex = 0
		For nAlgIndex=0 To CspAlgorithms.Count-1
			If CspAlgorithms.ItemByIndex(nAlgIndex).Name = "sha1" Then
				nBestIndex = nAlgIndex
			End If
			If CspAlgorithms.ItemByIndex(nAlgIndex).Name = "md5" AND CspAlgorithms.ItemByIndex(nBestIndex).Name <> "sha1" Then
				nBestIndex = nAlgIndex
			End If
		Next
		Set CspAlgorithm = CspAlgorithms.ItemByIndex(nBestIndex)
		If CspAlgorithm.Type = 2 Then	'XCN_CRYPT_HASH_INTERFACE
			g_objRequest.HashAlgorithm = CspAlgorithm.GetAlgorithmOid(0, 0)	', AlgorithmFlagsNone
		End if
		' Try to create request
		g_objEnroll.InitializeFromRequest(g_objRequest)
		GetCSR = g_objEnroll.CreateRequest(3)	'CRYPT_STRING_BASE64REQUESTHEADER
		if len(GetCSR)<>0 then Exit Function
	End Function	'GetCSR
 
	Function ControlExists(objectID)
		on error resume next
		ControlExists = IsObject(CreateObject(objectID))
	End Function
 
	' Used by both post and pre Vista code	
	Sub GenReq_OnClick
		Dim TheForm, result
		Set TheForm = Document.CertReqForm
		err.clear
		If InStr(Navigator.UserAgent, "Windows NT 6") <> 0 Then
			If g_certEnrollLoadError <> 0 Then
				Call MsgBox("Could not load CertEnroll.", 0, "Alert")
				Exit Sub
			End if
			If Document.CertReqForm.exportable.checked then
				result = GetCSR(1)
			Else
				result = GetCSR(0)
			End If
		Else
			If useold Then
				If Document.CertReqForm.exportable.checked then
					result = OldCSR(2 + 1)
				Else
					result = OldCSR(2)
				End If
			Else
				If Document.CertReqForm.exportable.checked then
					result = NewCSR(2 + 1)
				Else
					result = NewCSR(2)
				End If
			End If
		End If
		if len(result)=0 Then
			result = MsgBox("Unable to generate PKCS#10 certificate request.", 0, "Alert")
			Exit Sub
		End If
		TheForm.pkcs10req.Value = result
		TheForm.Submit
		Exit Sub
	End Sub	'GenReq_OnClick
	
	Function startup()
		If InStr(Navigator.UserAgent, "Windows NT 6") <> 0 Then
			InitVistaCSP()
		End if
 
		If InStr(Navigator.UserAgent, "Windows NT 6") <> 0 Then
			Call GetCertEnrollCSPList()
			Document.CertReqForm.classid.value = "clsid:884e2049-217d-11da-b2a4-000e7bbb2b09"
		Else
			GetProviderList()
		End If
	End Function		