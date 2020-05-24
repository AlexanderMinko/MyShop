<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
	isELIgnored="false" trimDirectiveWhitespaces="true"%>

<div class="visible-xs-block xs-option-container">
	<a class="pull-right" data-toggle="collapse" href="#productCatalog"> Product catalog <span class="caret"></span></a>
	<a data-toggle="collapse" href="#find">Find<span class="caret"></span></a>
</div>
<!--Search form-->
<form class="search" action="/search">
	<div id="find" class="panel panel-success collapse">
		<div class="panel-heading">Find</div>
		<div class="panel-body">
			<div class="input-group">
				<input type="text" name="query" class="form-control" placeholder="Search query..."> <span class="input-group-btn">
					 <a id="goSearch" class="btn btn-default">Go!</a>
				</span>
			</div>
			<div class="more-options">
				<a data-toggle="collapse" href="#serchOptions">More filters<span class="caret"></span></a>
			</div>
		</div>
		<div id="serchOptions" class="collapse">
			<!--pullout menu filters-->
			<div class="panel-heading">Category filters</div>
			<div class="panel-body categories">
				<!--CATEGORIES-->
				<label><input type="checkbox" id="allCategories">All</label>
				<div class="form-group">
					<div class="checkbox">
						<label><input type="checkbox" name="category" value="1"
							class="search-option">Notebooks</label>
					</div>
				</div>
				<div class="form-group">
					<div class="checkbox">
						<label><input type="checkbox" name="category" value="2"
							class="search-option">Phones</label>
					</div>
				</div>
			</div>
			<!--/CATEGORIES-->
			<div class="panel-heading">Producers filters</div>
			<div class="panel-body producers">
				<!--/PRODUCERS-->
				<label><input type="checkbox" id="allProducers">All</label>
				<div class="form-group">
					<div class="checkbox">
						<label><input type="checkbox" name="producer" value="1"
							class="search-option"> Check me out1</label>
					</div>
				</div>
				<div class="form-group">
					<div class="checkbox">
						<label><input type="checkbox" name="producer" value="2"
							class="search-option"> Check me out2</label>
					</div>
				</div>
			</div>
			<!--/PRODUCERS-->
		</div>
		<!--/pullout menu filters-->
	</div>
</form>
<!--/Search form-->
<!--Categories-->
<div id="productCatalog" class="panel panel-success collapse">
	<div class="panel-heading">Product catalog</div>
	<div class="list-group">
		<a href="#" class="list-group-item"><span class="badge">12</span>Notebooks</a>
		<a href="#" class="list-group-item"><span class="badge">13</span>Dapibus ac facilisis in</a>
		<a href="#" class="list-group-item"><span class="badge">45</span>Morbi leo risus</a>
		<a href="#" class="list-group-item"><span class="badge">17</span>Porta acconsectetur ac</a>
		<a href="#" class="list-group-item"><span class="badge">23</span>Vestibulum at eros</a>
	    <a href="#" class="list-group-item"><span class="badge">34</span>Vestibulum</a>
	    <a href="#" class="list-group-item"><span class="badge">19</span>toys</a>
	</div>
</div>