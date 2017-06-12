public class MyDate {
    private String month;
    private int date;
    private int year;

    public MyDate(String creationDate) {
        this.year = Integer.parseInt(creationDate.substring(creationDate.length() - 4));
        this.date = Integer.parseInt(creationDate.substring(8, 10));
        String monthString = creationDate.substring(4, 7);
        switch (monthString) {
            case "Jan":
                this.month = "01 Jan";
                break;
            case "Feb":
                this.month = "02 Feb";
                break;
            case "Mar":
                this.month = "03 Mar";
                break;
            case "Apr":
                this.month = "04 Apr";
                break;
            case "May":
                this.month = "05 May";
                break;
            case "Jun":
                this.month = "06 Jun";
                break;
            case "Jul":
                this.month = "07 Jul";
                break;
            case "Aug":
                this.month = "08 Aug";
                break;
            case "Sep":
                this.month = "09 Sep";
                break;
            case "Oct":
                this.month = "10 Oct";
                break;
            case "Nov":
                this.month = "11 Nov";
                break;
            case "Dec":
                this.month = "12 Dec";
                break;

        }
    }

    public String getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public int getYear() {
        return year;
    }
}
