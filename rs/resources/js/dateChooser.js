


//set todays date
Now = new Date();
NowDay = Now.getDate();
NowMonth = Now.getMonth();
NowYear = Now.getYear();
if (NowYear < 2000) NowYear += 1900; //for Netscape

//function for returning how many days there are in a month including leap years
function DaysInMonth(WhichMonth, WhichYear)
{
  var DaysInMonth = 31;
  if (WhichMonth == "4" || WhichMonth == "6" || WhichMonth == "9" || WhichMonth == "11") DaysInMonth = 30;
  if (WhichMonth == "2" && (WhichYear/4) != Math.floor(WhichYear/4))	DaysInMonth = 28;
  if (WhichMonth == "2" && (WhichYear/4) == Math.floor(WhichYear/4))	DaysInMonth = 29;
  if (WhichMonth == "0")	DaysInMonth = 0;
  return DaysInMonth;
}

//function to change the available days in a months
function ChangeOptionDays(formName)
{	
  var fullName =  "dgf_" + formName + "_fd-";
  DaysObject = getElement(fullName + "day");
  MonthObject = getElement(fullName + "month");
  YearObject = getElement(fullName + "year");

  Month = MonthObject[MonthObject.selectedIndex].value;  
  Year = YearObject[YearObject.selectedIndex].text;
  DaysForThisSelection = DaysInMonth(Month, Year);
  CurrentDaysInSelection = DaysObject.length;
  if ((CurrentDaysInSelection - 1)  > DaysForThisSelection)
  {
    for (i=0; i < ((CurrentDaysInSelection - 1) - DaysForThisSelection ); i++)
    {
      DaysObject.options[DaysObject.options.length - 1] = null
    }
  }
  if (DaysForThisSelection > (CurrentDaysInSelection - 1))
  {
    for (i=0; i < (DaysForThisSelection - (CurrentDaysInSelection - 1)); i++)
    {    
      var NewOption = new Option(DaysObject.options.length);
      DaysObject.options.add(NewOption,i+CurrentDaysInSelection);
    }
  }

  if (DaysObject.selectedIndex < 0) DaysObject.selectedIndex == 0;
}


function updateDateChooser(formName){
	var s ='';
	var index = 0;
	var fullName =  "dgf_" + formName + "_fd-";
	periodRadioButton = document.forms["mainform"].elements[fullName + "sales_price_period_type"]

  	fromDate = getElement(fullName + "from_date");
  	fromDateTrigger = getElement(fullName + "from_date_trigger");

  	toDate = getElement(fullName + "to_date");
  	toDateTrigger = getElement(fullName + "to_date_trigger");

	for(i=0; i < periodRadioButton.length; i++){
		if(periodRadioButton[i].checked){
			s = periodRadioButton[i].value;
			index = i;
		}
	}

  	if(s == 'id_actual' || s == ''){
	  	fromDate.disabled 		= true;
	  	toDate.disabled 		= true;
	  	fromDateTrigger.disabled= true;
	  	toDateTrigger.disabled 	= true;
	  	fromDate.value	 		= '';
	  	toDate.value 			= '';
  	}else{
	  	fromDate.disabled 		= false;
	  	toDate.disabled 		= false;  	
	  	fromDateTrigger.disabled= false;	  	
	  	toDateTrigger.disabled 	= false;
  	}
}

