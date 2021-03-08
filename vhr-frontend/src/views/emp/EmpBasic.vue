<template>
  <div>
    <div class="head-bar">
      <div>
        <el-input
            size="small"
            placeholder="通过姓名搜索员工"
            prefix-icon="el-icon-search"
            v-model="empName"
            @keydown.enter.native="loadEmps"
            clearable
            @clear="loadEmps"
            style="width: 300px; margin-right: 10px"
            :disabled="advanceBoxVisible">
        </el-input>
        <el-button size="small" type="primary" icon="el-icon-search" @click="loadEmps" :disabled="advanceBoxVisible">
          搜索
        </el-button>
        <el-button size="small" type="primary" :icon="advanceIcon" @click="changeAdvanceBox">高级搜索
        </el-button>
      </div>
      <div>
        <el-button class="right-btn" size="small" type="primary" icon="el-icon-plus" @click="openAddBox">添加员工
        </el-button>
        <el-dialog
            :title="title"
            :visible.sync="dialogVisible"
            @close="cleanEmp"
            width="65%">
          <div style="padding: 0 15px 0 15px">
            <el-form :model="emp" :rules="rules" ref="empForm">
              <el-row>
                <el-col :span="6">
                  <el-form-item label="姓名：" prop="name">
                    <el-input size="mini" style="width: 180px" prefix-icon="el-icon-edit" v-model="emp.name"
                              placeholder="请输入员工姓名"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="性别：" prop="gender">
                    <el-radio-group v-model="emp.gender">
                      <el-radio label="男">男</el-radio>
                      <el-radio label="女">女</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="出生日期：" prop="birthday">
                    <el-date-picker
                        v-model="emp.birthday"
                        style="width: 180px"
                        size="mini"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <el-form-item label="政治面貌：" prop="politicId">
                    <el-select v-model="emp.politicId" placeholder="请选择政治面貌" size="mini" style="width: 200px">
                      <el-option
                          v-for="item in politicsstatus"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="民族：" prop="nationId">
                    <el-select v-model="emp.nationId" placeholder="请选择民族" size="mini" style="width: 180px">
                      <el-option
                          v-for="item in nations"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="籍贯：" prop="nativePlace">
                    <el-input size="mini" style="width: 150px" v-model="emp.nativePlace" placeholder="请输入籍贯"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="电子邮箱：" prop="email">
                    <el-input size="mini" prefix-icon="el-icon-message" style="width: 180px" v-model="emp.email"
                              placeholder="请输入邮箱"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <el-form-item label="联系地址：" prop="address">
                    <el-input size="mini" prefix-icon="el-icon-s-home" style="width: 200px" v-model="emp.address"
                              placeholder="请输入地址"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="职位：" prop="posId">
                    <el-select v-model="emp.posId" placeholder="请选择职位" size="mini" style="width: 180px">
                      <el-option
                          v-for="item in positions"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="职称：" prop="jobLevelId">
                    <el-select v-model="emp.jobLevelId" placeholder="请选择职称" size="mini" style="width: 180px">
                      <el-option
                          v-for="item in jobLevels"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="所属部门：" prop="departmentId" style="margin-bottom: 0">
                    <el-popover
                        placement="right"
                        title="选择部门"
                        width="300"
                        trigger="manual"
                        v-model="deptSelectVisible"
                        ref="popover0">
                      <el-tree
                          default-expand-all
                          :data="deptTree"
                          :props="defaultProps"
                          @node-click="handleNodeClick"
                          ref="tree0">
                      </el-tree>
                      <div slot="reference" @click="openDeptSelect"
                           style="height: 28px; width: 180px; border: 1px solid #dedede; margin-top: 7px;
                           display: inline-flex; font-size: 13px; align-items: center; border-radius: 5px;
                           cursor: pointer; padding-left: 15px; box-sizing: border-box">
                        {{ selectedDept }}
                      </div>
                    </el-popover>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="电话号码：" prop="phone">
                    <el-input size="mini" prefix-icon="el-icon-phone" style="width: 180px" v-model="emp.phone"
                              placeholder="请输入电话号码"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="工号：" prop="workId">
                    <el-input disabled size="mini" style="width: 180px" v-model="emp.workId"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="学历：" prop="tiptopDegree">
                    <el-select v-model="emp.tiptopDegree" placeholder="请选择学历" size="mini" style="width: 180px">
                      <el-option
                          v-for="item in tiptopDegrees"
                          :key="item"
                          :label="item"
                          :value="item">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="毕业院校：" prop="school">
                    <el-input size="mini" prefix-icon="el-icon-school" style="width: 180px" v-model="emp.school"
                              placeholder="请输入学校名"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="专业名称：" prop="specialty">
                    <el-input size="mini" prefix-icon="el-icon-document" style="width: 180px" v-model="emp.specialty"
                              placeholder="请输入专业名"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="入职日期：" prop="beginDate">
                    <el-date-picker
                        v-model="emp.beginDate"
                        style="width: 152px"
                        size="mini"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="转正日期：" prop="conversionTime">
                    <el-date-picker
                        v-model="emp.conversionTime"
                        style="width: 152px"
                        size="mini"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="合同起始日期：" prop="beginContract">
                    <el-date-picker
                        v-model="emp.beginContract"
                        style="width: 152px"
                        size="mini"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="合同终止日期：" prop="endContract">
                    <el-date-picker
                        v-model="emp.endContract"
                        style="width: 152px"
                        size="mini"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="身份证号：" prop="idCard">
                    <el-input size="mini" prefix-icon="el-icon-postcard" style="width: 200px" v-model="emp.idCard"
                              placeholder="请输入身份证号"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="聘用形式：" prop="engageForm">
                    <el-radio-group v-model="emp.engageForm">
                      <el-radio label="劳动合同">劳动合同</el-radio>
                      <el-radio label="劳务合同">劳务合同</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="婚姻状况：" prop="wedlock">
                    <el-radio-group v-model="emp.wedlock">
                      <el-radio label="未婚">未婚</el-radio>
                      <el-radio label="已婚">已婚</el-radio>
                      <el-radio label="离异">离异</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addEmp">确 定</el-button>
          </span>
        </el-dialog>

        <el-upload
            :disabled="importDisabled"
            :before-upload="beforeUpload"
            :on-success="onSuccess"
            class="right-btn"
            action="/emp/basic/import"
            :show-file-list="false">
          <el-button size="small" type="success" :icon="importBtnIcon" :disabled="importDisabled">{{
              importBtnText
            }}
          </el-button>
        </el-upload>
        <el-button class="right-btn" size="small" type="success" @click="exportData" icon="el-icon-download">导出数据
        </el-button>
      </div>
    </div>

    <transition name="slide-fade">
      <div class="advanced-search-box" v-show="advanceBoxVisible">
        <el-row>
          <el-col :span="5">
            政治面貌:
            <el-select v-model="advancedData.politicId" placeholder="请选择政治面貌" size="mini" style="width: 200px" clearable>
              <el-option
                  v-for="item in politicsstatus"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            民族:
            <el-select v-model="advancedData.nationId" placeholder="请选择民族" size="mini" style="width: 180px" clearable>
              <el-option
                  v-for="item in nations"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            职位:
            <el-select v-model="advancedData.posId" placeholder="请选择职位" size="mini" style="width: 180px" clearable>
              <el-option
                  v-for="item in positions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="4">
            职称:
            <el-select v-model="advancedData.jobLevelId" placeholder="请选择职称" size="mini" style="width: 180px" clearable>
              <el-option
                  v-for="item in jobLevels"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="7">
            聘用形式:
            <el-radio-group v-model="advancedData.engageForm">
              <el-radio label="劳动合同">劳动合同</el-radio>
              <el-radio label="劳务合同">劳务合同</el-radio>
            </el-radio-group>
          </el-col>
        </el-row>
        <el-row style="margin-top: 8px">
          <el-col :span="5" style="display: inline-flex">
            所属部门:
            <el-popover
                placement="right"
                title="选择部门"
                width="300"
                trigger="manual"
                v-model="advancedDeptSelectVisible">
              <el-tree
                  default-expand-all
                  :data="deptTree"
                  :props="defaultProps"
                  @node-click="advanceNodeClick">
              </el-tree>
              <div slot="reference" @click="openDeptSelect('advanced')"
                   style="height: 28px; width: 200px; border: 1px solid #dedede; margin-left: 5px;
                           display: inline-flex; font-size: 13px; align-items: center; border-radius: 5px;
                           cursor: pointer; padding-left: 15px; box-sizing: border-box">
                {{ selectedDept }}
              </div>
            </el-popover>
          </el-col>
          <el-col :span="9">
            入职日期:
            <el-date-picker
                size="mini"
                v-model="advancedData.beginDateScope"
                unlink-panels
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                clearable>
            </el-date-picker>
          </el-col>
          <el-col :span="2" :offset="8">
            <el-button size="mini" @click="changeAdvanceBox">取 消</el-button>
            <el-button size="mini" type="primary" @click="loadEmps">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </transition>

    <div>
      <el-table
          :data="emps"
          stripe
          border
          v-loading="loading"
          style="width: 100%">
        <el-table-column
            type="selection"
            width="40">
        </el-table-column>
        <el-table-column
            prop="name"
            fixed
            align="left"
            label="姓名"
            width="100">
        </el-table-column>
        <el-table-column
            prop="workId"
            label="工号"
            align="left"
            width="90">
        </el-table-column>
        <el-table-column
            prop="gender"
            label="性别"
            align="left"
            width="60">
        </el-table-column>
        <el-table-column
            prop="birthday"
            label="出生日期"
            align="left"
            width="110">
        </el-table-column>
        <el-table-column
            prop="idCard"
            label="身份证号码"
            align="left"
            width="180">
        </el-table-column>
        <el-table-column
            prop="wedlock"
            label="婚姻状况"
            align="left"
            width="85">
        </el-table-column>
        <el-table-column
            prop="nation.name"
            label="民族"
            align="left">
        </el-table-column>
        <el-table-column
            prop="nativePlace"
            label="籍贯"
            align="left">
        </el-table-column>
        <el-table-column
            prop="politicsstatus.name"
            label="政治面貌"
            align="left">
        </el-table-column>
        <el-table-column
            prop="email"
            label="电子邮箱"
            align="left"
            width="180">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话号码"
            align="left"
            width="120">
        </el-table-column>
        <el-table-column
            prop="address"
            label="联系地址"
            align="left"
            width="150">
        </el-table-column>
        <el-table-column
            prop="department.name"
            label="所处部门"
            align="left"
            width="85">
        </el-table-column>
        <el-table-column
            prop="position.name"
            label="职位名称"
            align="left"
            width="100">
        </el-table-column>
        <el-table-column
            prop="jobLevel.name"
            label="职称名称"
            align="left"
            width="120">
        </el-table-column>
        <el-table-column
            prop="engageForm"
            label="聘用形式"
            align="left"
            width="85">
        </el-table-column>
        <el-table-column
            prop="beginDate"
            label="入职日期"
            align="left"
            width="110">
        </el-table-column>
        <el-table-column
            prop="conversionTime"
            label="转正日期"
            align="left"
            width="110">
        </el-table-column>
        <el-table-column
            prop="beginContract"
            label="合同起始日期"
            align="left"
            width="110">
        </el-table-column>
        <el-table-column
            prop="endContract"
            label="合同终止日期"
            align="left"
            width="110">
        </el-table-column>
        <el-table-column
            label="合同期限"
            align="left"
            width="85">
          <template slot-scope="scope">
            <el-tag size="mini">{{ scope.row.contractTerm }}</el-tag>
            年
          </template>
        </el-table-column>
        <el-table-column
            prop="tiptopDegree"
            label="最高学历"
            align="left"
            width="85">
        </el-table-column>
        <el-table-column
            label="操作"
            width="180"
            fixed="right">
          <template slot-scope="scope">
            <el-button style="padding: 5px" size="small" @click="openUpdateBox(scope.row)">编辑</el-button>
            <el-button style="padding: 5px" size="small" type="primary">高级资料</el-button>
            <el-button style="padding: 5px" size="small" type="danger" @click="deleteEmp(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="page-bar">
      <el-pagination
          background
          @size-change="sizeChange"
          @current-change="currentChange"
          layout="sizes, prev, pager, next, jumper, ->, total, slot"
          :total="totalPage">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "EmpBasic",
  data() {
    return {
      title: '',
      loading: false,
      empName: '',
      emps: [],
      searchType: null,
      pageNum: 1,
      pageSize: 10,
      totalPage: 0,
      dialogVisible: false,
      selectedDept: '',
      emp: {
        name: null,
        gender: null,
        birthday: null,
        idCard: null,
        wedlock: null,
        nationId: null,
        nativePlace: null,
        politicId: null,
        email: null,
        phone: null,
        address: null,
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: null,
        tiptopDegree: null,
        specialty: null,
        school: null,
        beginDate: null,
        workId: null,
        contractTerm: null,
        conversionTime: null,
        notworkDate: null,
        beginContract: null,
        endContract: null,
        workAge: null
      },
      rules: {
        name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
        gender: [{required: true, message: '请选择性别', trigger: 'blur'}],
        birthday: [{required: true, message: '请选择生日', trigger: 'blur'}],
        idCard: [{required: true, message: '请输入身份证号', trigger: 'blur'},
          {
            pattern: /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X])$/,
            message: '身份证号码不正确',
            trigger: 'blur'
          }],
        wedlock: [{required: true, message: '请选择婚姻状态', trigger: 'blur'}],
        nationId: [{required: true, message: '请选择民族', trigger: 'blur'}],
        nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
        politicId: [{required: true, message: '请选择政治面貌', trigger: 'blur'}],
        email: [{required: true, message: '请输入邮箱地址', trigger: 'blur'},
          {type: 'email', message: '邮箱名不合法', trigger: 'blur'}],
        phone: [{required: true, message: '请输入电话号码', trigger: 'blur'}],
        address: [{required: true, message: '请输入住址', trigger: 'blur'}],
        departmentId: [{required: true, message: '请选择部门', trigger: 'blur'}],
        jobLevelId: [{required: true, message: '请选择职称', trigger: 'blur'}],
        posId: [{required: true, message: '请选择职位', trigger: 'blur'}],
        engageForm: [{required: true, message: '请选择合同形式', trigger: 'blur'}],
        tiptopDegree: [{required: true, message: '请选择最高学历', trigger: 'blur'}],
        specialty: [{required: true, message: '请输入专业名称', trigger: 'blur'}],
        school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
        beginDate: [{required: true, message: '请选择入职时间', trigger: 'blur'}],
        workId: [{required: true, message: '工号有误，请联系工作人员', trigger: 'blur'}],
        conversionTime: [{required: true, message: '请选择转正时间', trigger: 'blur'}],
        beginContract: [{required: true, message: '请选择合同起始时间', trigger: 'blur'}],
        endContract: [{required: true, message: '请选择合同终止时间', trigger: 'blur'}],
      },
      nations: [],
      jobLevels: [],
      politicsstatus: [],
      positions: [],
      tiptopDegrees: ['小学', '初中', '高中', '中专', '大专', '本科', '硕士', '博士'],
      deptSelectVisible: false,
      advancedDeptSelectVisible: false,
      deptTree: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      advancedData: {
        nationId: null,
        politicId: null,
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: null,
        beginDateScope: null,
      },
      advanceBoxVisible: false,
      advanceIcon: 'el-icon-arrow-down',
      importBtnText: '导入数据',
      importBtnIcon: 'el-icon-upload2',
      importDisabled: false,
    }
  },
  mounted() {
    this.loadEmps();
  },
  methods: {
    sizeChange(currentSize) {
      this.pageSize = currentSize;
      this.loadEmps();
    },
    currentChange(currentPage) {
      this.pageNum = currentPage;
      this.loadEmps();
    },
    loadEmps() {
      this.loading = true;
      let url = "/emp/basic/?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize;
      if (this.searchType != null && this.searchType == 'advanced') {
        if (this.advancedData.departmentId != null) {
          url += '&departmentId=' + this.advancedData.departmentId;
        }
        if (this.advancedData.nationId != null) {
          url += '&nationId=' + this.advancedData.nationId;
        }
        if (this.advancedData.jobLevelId != null) {
          url += '&jobLevelId=' + this.advancedData.jobLevelId;
        }
        if (this.advancedData.posId != null) {
          url += '&posId=' + this.advancedData.posId;
        }
        if (this.advancedData.politicId != null) {
          url += '&politicId=' + this.advancedData.politicId;
        }
        if (this.advancedData.engageForm != null) {
          url += '&engageForm=' + this.advancedData.engageForm;
        }
        if (this.advancedData.beginDateScope != null) {
          url += '&beginDateScope=' + this.advancedData.beginDateScope[0] + '&beginDateScope=' + this.advancedData.beginDateScope[1];
        }
      } else {
        url += "&name=" + this.empName
      }
      this.getRequest(url).then(resp => {
        this.loading = false;
        if (resp) {
          this.emps = resp.list;
          this.totalPage = resp.total;
        }
      })
    },
    changeAdvanceBox() {
      if (this.advanceBoxVisible) {
        this.searchType = null;
        this.advanceBoxVisible = false;
        this.advanceIcon = 'el-icon-arrow-down';
      } else {
        this.searchType = 'advanced'
        this.loadAddBoxData();
        this.advanceBoxVisible = true;
        this.advanceIcon = 'el-icon-arrow-up';
      }
    },
    advanceNodeClick(node) {
      this.selectedDept = node.name;
      this.advancedData.departmentId = node.id;
      this.advancedDeptSelectVisible = false;
    },
    openAddBox() {
      this.selectedDept = null;
      this.title = '添加员工';
      this.dialogVisible = true;
      this.loadAddBoxData();
      this.getRequest("/emp/basic/workId").then(resp => {
        if (resp) {
          this.emp.workId = resp.obj;
        }
      })
    },
    loadAddBoxData() {
      if (!window.sessionStorage.getItem("nations")) {
        this.getRequest("/emp/basic/nations").then(resp => {
          if (resp) {
            this.nations = resp;
            window.sessionStorage.setItem("nations", JSON.stringify(resp))
          }
        })
      } else {
        this.nations = JSON.parse(window.sessionStorage.getItem("nations"))
      }
      if (!window.sessionStorage.getItem("politicsstatus")) {
        this.getRequest("/emp/basic/politicsstatus").then(resp => {
          if (resp) {
            this.politicsstatus = resp;
            window.sessionStorage.setItem("politicsstatus", JSON.stringify(resp))
          }
        })
      } else {
        this.politicsstatus = JSON.parse(window.sessionStorage.getItem("politicsstatus"))
      }
      this.getRequest("/emp/basic/positions").then(resp => {
        if (resp) {
          this.positions = resp;
        }
      })
      this.getRequest("/emp/basic/jobLevels").then(resp => {
        if (resp) {
          this.jobLevels = resp;
        }
      })
    },
    openDeptSelect(type) {
      if (type != null && type == 'advanced') {
        this.advancedDeptSelectVisible = true;
      } else {
        this.deptSelectVisible = true;
      }
      this.getRequest("/emp/basic/departments").then(resp => {
        if (resp) {
          this.deptTree = resp;
        }
      })
    },
    handleNodeClick(node) {
      this.selectedDept = node.name;
      this.emp.departmentId = node.id;
      this.deptSelectVisible = false;
    },
    addEmp() {
      if (this.emp.id) {
        this.$refs['empForm'].validate(valid => {
          if (valid) {
            this.putRequest("/emp/basic/", this.emp).then(resp => {
              if (resp) {
                this.loadEmps();
                this.dialogVisible = false;
              }
            })
          }
        })
      } else {
        this.$refs['empForm'].validate(valid => {
          if (valid) {
            this.postRequest("/emp/basic/", this.emp).then(resp => {
              if (resp) {
                this.loadEmps();
                this.dialogVisible = false;
              }
            })
          }
        })
      }
    },
    openUpdateBox(emp) {
      this.selectedDept = null;
      this.title = '修改员工信息';
      this.emp = emp;
      this.dialogVisible = true;
      this.loadAddBoxData();
    },
    cleanEmp() {
      this.emp = {
        name: null,
        gender: null,
        birthday: null,
        idCard: null,
        wedlock: null,
        nationId: null,
        nativePlace: null,
        politicId: null,
        email: null,
        phone: null,
        address: null,
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: null,
        tiptopDegree: null,
        specialty: null,
        school: null,
        beginDate: null,
        workState: null,
        workId: null,
        contractTerm: null,
        conversionTime: null,
        notworkDate: null,
        beginContract: null,
        endContract: null,
        workAge: null
      };
      this.selectedDept = null;
    },
    deleteEmp(emp) {
      this.$confirm('此操作将永久删除员工【' + emp.name + '】, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteRequest("/emp/basic/" + emp.id).then(resp => {
          if (resp) {
            this.loadEmps();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    exportData() {
      window.open("/emp/basic/export", "_parent");
    },
    beforeUpload() {
      this.importBtnText = '正在上传';
      this.importBtnIcon = 'el-icon-loading';
      this.importDisabled = true;
    },
    onSuccess() {
      this.importBtnIcon = 'el-icon-check'
      this.importBtnText = '';
      setTimeout(() => {
        this.importBtnIcon = 'el-icon-upload2';
        this.importBtnText = '导入数据';
      }, 500);
      this.importDisabled = false;
      this.loadEmps()
    }
  }
}
</script>
<style>
.head-bar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.advanced-search-box {
  width: 100%;
  border-radius: 8px;
  border: 2px solid #388bde;
  margin-bottom: 15px;
  padding: 5px 15px;
  box-sizing: border-box;
}

.page-bar {
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
}

.right-btn {
  display: inline-flex;
  margin-left: 10px;
}

.slide-fade-enter-active {
  transition: all .3s ease;
}

.slide-fade-leave-active {
  transition: all .5s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slide-fade-enter, .slide-fade-leave-to
  /* .slide-fade-leave-active for below version 2.1.8 */
{
  transform: translateX(10px);
  opacity: 0;
}
</style>
