<table  border="2px">
	<tr>
		<td colspan="5"><p>啟動時間:${checkTime}</p></td>
	</tr>
	<tr>
		<th style="text-align: left">訊息別</th>
		<th style="text-align: left">交易序號</th>
		<th style="text-align: left">上一次執行時間</th>
	</tr>
	
	
	 <#list items as item>
		<tr>
			<td>${item.msgType}</td>
			<td>${item.transactionId}</td>
			<td>${item.startTime}</td>
		</tr>
	</#list>
	
</table>
	

	
